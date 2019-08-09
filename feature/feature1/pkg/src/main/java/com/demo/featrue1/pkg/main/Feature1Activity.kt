package com.demo.featrue1.pkg.main

import android.content.Context
import android.os.Bundle
import android.view.View
import com.demo.common.CommonTitleActivity
import com.demo.featrue1.pkg.R

class Feature1Activity : CommonTitleActivity() {

    companion object {
        fun start(context: Context): String {
            return "hello from feature1"
        }
    }

    override fun bindTitle(): CharSequence {
        return "feature1"
    }

    override fun bindLayout(): Int {
        return R.layout.activity_feature1
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {

    }

    override fun doBusiness() {

    }


}
