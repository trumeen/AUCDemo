package com.demo.featrue1.pkg

import android.content.Context
import com.blankj.utilcode.util.ApiUtils
import com.blankj.utilcode.util.LogUtils
import com.demo.featrue1.pkg.main.Feature1Activity
import com.demo.feature1.export.Feature1Api
import com.demo.feature1.export.Feature1Param
import com.demo.feature1.export.Feature1Result

@ApiUtils.Api
class Feature1ApiImpl : Feature1Api() {
    override fun startFeature1Activity(context: Context, param: Feature1Param): Feature1Result {
        LogUtils.d(param.name)
        return Feature1Result(Feature1Activity.start(context))
    }
}