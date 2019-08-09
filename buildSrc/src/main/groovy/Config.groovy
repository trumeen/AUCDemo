class Config {
    static applicationId = 'com.galanz.aucframetemplate'
    static appName = 'AucFrame'
    static compileSdkVersion = 28
    static minSdkVersion = 21
    static targetSdkVersion = 28
    static versionCode = 1
    static versionName = '1.0.0'
    static kotlin_version = '1.3.41'
    static support_version = '1.1.0-rc01'
    static leakcanary_version = '1.6.3'

    //appConfig 配置的是可以跑app模块的，git 提交务必只包含launcher
    static appConfig = ['launcher']
    //pkgConfig配置的是需要依赖的功能包，为空则全部依赖，git提交务必为空
    static pkgConfig = ["feature0"]

    static depConfig = [
            feature      : [
                    launcher: [
                            app: new DepConfig(":feature:launcher:app")
                    ],
                    feature0: [
                            app   : new DepConfig(":feature:feature0:app"),
                            pkg   : new DepConfig(true, ":feature:feature0:pkg",
                                    "com.blankj:feature-feature0-pkg:1.0", true),
                            export: new DepConfig(":feature:feature0:export"),
                    ],
                    feature1: [
                            app   : new DepConfig(":feature:feature1:app"),
                            pkg   : new DepConfig(":feature:feature1:pkg"),
                            export: new DepConfig(":feature:feature1:export"),
                    ],
                    mock : new DepConfig(":feature:mock"),
            ],
            lib          : [
                    base  : new DepConfig(":lib:base"),
                    common: new DepConfig(":lib:common"),
            ],
            plugin       : [
                    gradle: new DepConfig("com.android.tools.build:gradle:3.4.0"),
                    kotlin: new DepConfig("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"),
                    api   : new DepConfig("com.blankj:api-gradle-plugin:1.0"),
            ],
            support      : [
                    appcompat_v7: new DepConfig("androidx.appcompat:appcompat:$support_version"),
                    design      : new DepConfig("com.google.android.material:material:1.0.0"),
                    multidex    : new DepConfig("com.android.support:multidex:1.0.2"),
                    constraint  : new DepConfig("androidx.constraintlayout:constraintlayout:1.1.3"),
            ],
            kotlin       : new DepConfig("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"),
            utilcode     : new DepConfig("com.blankj:utilcode:1.25.0"),
            free_proguard: new DepConfig("com.blankj:free-proguard:1.0.1"),
            swipe_panel  : new DepConfig("com.blankj:swipe-panel:1.1"),
            leakcanary   : [
                    android         : new DepConfig("com.squareup.leakcanary:leakcanary-android:$leakcanary_version"),
                    android_no_op   : new DepConfig("com.squareup.leakcanary:leakcanary-android-no-op:$leakcanary_version"),
                    support_fragment: new DepConfig("com.squareup.leakcanary:leakcanary-support-fragment:$leakcanary_version"),
            ],

    ]

}