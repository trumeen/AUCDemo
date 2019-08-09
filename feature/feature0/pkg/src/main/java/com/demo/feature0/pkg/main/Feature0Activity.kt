package com.demo.feature0.pkg.main

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ApiUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.demo.common.CommonTitleActivity
import com.demo.feature0.pkg.R
import com.demo.feature1.export.Feature1Api
import com.demo.feature1.export.Feature1Param
import com.demo.feature1.export.Feature1Result
import kotlinx.android.synthetic.main.activity_feature0.*

class Feature0Activity : CommonTitleActivity() {
    override fun bindTitle(): CharSequence {

        return "feature0"
    }

    override fun bindLayout(): Int {
        return R.layout.activity_feature0
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        btn.setOnClickListener {
            var result = ApiUtils.getApi(Feature1Api::class.java)
                .startFeature1Activity(this, Feature1Param("Feature1Param"))
            ToastUtils.showShort(result.name)
        }
        iv_img.load(R.mipmap.ic_launcher)
    }

    override fun doBusiness() {
    }


}
