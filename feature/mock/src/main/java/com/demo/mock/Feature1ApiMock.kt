package com.demo.mock

import android.content.Context
import com.blankj.utilcode.util.ApiUtils
import com.blankj.utilcode.util.LogUtils
import com.demo.feature1.export.Feature1Api
import com.demo.feature1.export.Feature1Param
import com.demo.feature1.export.Feature1Result

@ApiUtils.Api(isMock = true)
class Feature1ApiMock : Feature1Api() {
    override fun startFeature1Activity(context: Context, param: Feature1Param): Feature1Result {
        LogUtils.d(param.name)
        return Feature1Result("hello from mock")
    }
}