import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Project
import org.gradle.api.ProjectEvaluationListener
import org.gradle.api.ProjectState
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle

class ConfigUtils {
    static addBuildListener(Gradle g) {
        g.addBuildListener(new BuildListener() {
            @Override
            void buildStarted(Gradle gradle) {
                GLog.d("buildStarted")
            }

            @Override
            void settingsEvaluated(Settings settings) {
                GLog.d("settingsEvaluated")
                includeModule(settings)
            }

            @Override
            void projectsLoaded(Gradle gradle) {
                GLog.d("projectsLoaded")
                generateDep(gradle)
                gradle.addProjectEvaluationListener(new ProjectEvaluationListener() {
                    @Override
                    void beforeEvaluate(Project project) {
                        GLog.d("beforeEvaluate --->" + project.name)
                        if (project.subprojects.isEmpty()) {
                            if (project.name == "app") {
                                GLog.l(project.toString() + " applies buildApp.gradle")
                                project.apply {
                                    from "${project.rootDir.path}/buildApp.gradle"
                                }
                            } else {
                                GLog.l(project.toString() + " applies buildLib.gradle")
                                project.apply {
                                    from "${project.rootDir.path}/buildLib.gradle"
                                }
                                if (project.name == "pkg") {
                                    addPkgLibs(project)
                                }
                            }
                        }
                    }

                    @Override
                    void afterEvaluate(Project project, ProjectState projectState) {
                        GLog.d("afterEvaluate --->" + project.name)
                    }
                })
            }

            @Override
            void projectsEvaluated(Gradle gradle) {
                GLog.d("projectsEvaluated")
            }

            @Override
            void buildFinished(BuildResult buildResult) {
                GLog.d("buildFinished")
            }
        })
    }

    private static includeModule(Settings settings) {
        if (Config.pkgConfig.isEmpty()) {
            Config.depConfig.feature.mock.isApply = false
        }
        def config = getDepConfigByFilter(new DepConfigFilter() {

            @Override
            boolean accept(String name, DepConfig config) {
                GLog.l("name--->" + name)
                if (name.endsWith('.app')) {
                    //获取APP模块的名字
                    def appName = name.substring('feature.'.length(), name.length() - 4)
                    //如果appConfig 不存在则不加入依赖
                    if (!Config.appConfig.contains(appName)) {
                        config.isApply = false
                    }
                }
                //如果pkgConfig 不为空则为pkg调试模式
                if (!Config.pkgConfig.isEmpty()) {
                    if (name.endsWith(".pkg")) {
                        //获取pkg 模块的名字
                        def pkgName = name.substring('feature.'.length(), name.length() - 4)
                        GLog.l("pkg module name --->" + pkgName)
                        if (!Config.pkgConfig.contains(pkgName)) {
                            config.isApply = false
                        }
                    }
                }
                if (!config.isApply) return false
                if (!config.useLocal) return false
                if (config.localPath == "") return false
                return true
            }
        }).each { _, cfg ->// 把本地模块include进去
            settings.include cfg.localPath
        }
        GLog.l("includeModule = ${GLog.object2String(config)}")

    }

    /**
     * 向模块中添加模块依赖
     * @param project
     */
    private static addPkgLibs(Project project) {
        def keys = project.path.split(":")
        if (keys.length == 4) {
            if (Config.depConfig[keys[1]] != null && Config.depConfig[keys[1]][keys[2]] != null) {
                if (Config.depConfig[keys[1]][keys[2]]["libs"] != null) {
                    for (Map.Entry entry : Config.depConfig[keys[1]][keys[2]]['libs'].entrySet()) {
                        GLog.d("addPkgLibs add dependence-->" + entry.key + " " + entry.value)
                        if (entry.value.isApply) {
                            project.dependencies.add("implementation", entry.value.dep)
                        }
                    }
                }
            }
        }


    }

    /**
     * 根据 depConfig 生成 dep
     */
    private static generateDep(Gradle gradle) {
        def config = getDepConfigByFilter(new DepConfigFilter() {
            @Override
            boolean accept(String name, DepConfig config) {
                if (config.useLocal) {// 如果使用的是本地模块，则转化为 project
                    config.dep = gradle.rootProject.findProject(config.localPath)
                } else {// 如果是远端依赖，那就直接引用
                    config.dep = config.remotePath
                }
                return true
            }
        })
        GLog.l("generateDep = ${GLog.object2String(config)}")
    }
    /**
     * 根据过滤器来获取 DepConfig
     */
    static Map<String, DepConfig> getDepConfigByFilter(DepConfigFilter filter) {
        return _getDepConfigByFilter("", Config.depConfig, filter)
    }

    private static _getDepConfigByFilter(String namePrefix, Map map, DepConfigFilter
            filter) {
        def depConfigList = [:]//结果Map
        for (Map.Entry entry : map.entrySet()) {
            def (name, value) = [entry.getKey(), entry.getValue()]
            if (value instanceof Map) {// 如果值是Map类型就加到Map中
                namePrefix += (name + '.')
                depConfigList.putAll(_getDepConfigByFilter(namePrefix, value,
                        filter))
                namePrefix -= (name + '.')
                continue
            }
            def config = value as DepConfig
            if (filter == null || filter.accept(namePrefix + name, config)) {
                depConfigList.put(namePrefix + name, config)//符合过滤条件的就加入到Map中
            }
        }
        return depConfigList
    }

    static getApplyPkgs() {
        def applyPkgs = getDepConfigByFilter(new DepConfigFilter() {
            @Override
            boolean accept(String name, DepConfig config) {
                if (!config.isApply) return false
                return name.endsWith(".pkg")
            }
        })
        GLog.d("getApplyPkgs = ${GLog.object2String(applyPkgs)}")
        return applyPkgs
    }

    static getApplyPkgLibs() {
        def applyLibs = getDepConfigByFilter(new DepConfigFilter() {
            @Override
            boolean accept(String name, DepConfig config) {
                if (!config.isApply) return false
                return name.contains(".libs")
            }
        })
        GLog.d("getLibsPkgs = ${GLog.object2String(applyLibs)}")
        return applyLibs
    }

    static getApplyExports() {
        def applyExports = getDepConfigByFilter(new DepConfigFilter() {
            @Override
            boolean accept(String name, DepConfig config) {
                if (!config.isApply) return false
                return name.endsWith(".export")
            }
        })
        GLog.d("getApplyExports = ${GLog.object2String(applyExports)}")
        return applyExports
    }

    static getApplyPlugins() {
        def plugins = getDepConfigByFilter(new DepConfigFilter() {
            @Override
            boolean accept(String name, DepConfig config) {
                if (!name.startsWith("plugin.")) return false
                if (!config.isApply) return false
                return true
            }
        })
        GLog.d("getApplyPlugins = ${GLog.object2String(plugins)}")
        return plugins
    }


    interface DepConfigFilter {
        boolean accept(String name, DepConfig config);
    }
}