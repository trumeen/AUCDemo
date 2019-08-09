package com.demo.feature1.export

import android.content.Context
import com.blankj.utilcode.util.ApiUtils

abstract class Feature1Api: ApiUtils.BaseApi() {
    abstract fun startFeature1Activity(
        context: Context,
        param: Feature1Param
    ): Feature1Result
}

class Feature1Param(var name: String?)
class Feature1Result(var name: String?)