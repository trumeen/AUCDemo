class DepConfig {
    boolean useLocal //是否使用本地
    String localPath //本地路径
    String remotePath//远程路径
    boolean isApply //是否应用
    String path //最后的路径
    def dep //根据条件生成最终的依赖项
    DepConfig(String path) {
        this(path, true)
    }

    DepConfig(String path, boolean isApply) {
        if (path.startsWith(":")) {
            this.useLocal = true
            this.localPath = path
            this.isApply = isApply
        } else {
            this.useLocal = false
            this.remotePath = path
            this.isApply = isApply
        }
        this.path = path
    }

    DepConfig(boolean useLocal, String localPath, String remotePath) {
        this(useLocal, localPath, remotePath, true)
    }

    DepConfig(boolean useLocal, String localPath, String remotePath, boolean
            isApply) {
        this.useLocal = useLocal
        this.localPath = localPath
        this.remotePath = remotePath
        this.isApply = isApply
        this.path = useLocal ? localPath : remotePath
    }

    @Override
    String toString() {
        return "DepConfig { " +
                "useLocal = " + useLocal +
                (dep == null ? ", path = " + path : (", dep = " + dep)) +
                ", isApply = " + isApply +
                " }"
    }
}