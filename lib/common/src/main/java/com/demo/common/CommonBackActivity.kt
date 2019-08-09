package com.demo.common

import com.demo.base.BaseActivity
import com.blankj.swipepanel.SwipePanel
import com.blankj.utilcode.util.SizeUtils
import android.os.Bundle





abstract class CommonBackActivity: BaseActivity() {

    abstract fun isSwipeBack(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSwipeBack()
    }


    private fun initSwipeBack() {
        if (isSwipeBack()) {
            val swipeLayout = SwipePanel(this)
            swipeLayout.setLeftDrawable(R.drawable.base_back)
            swipeLayout.setLeftEdgeSize(SizeUtils.dp2px(100f))
            swipeLayout.wrapView(findViewById(android.R.id.content))
            swipeLayout.setOnFullSwipeListener { direction ->
                swipeLayout.close(direction)
                finish()
            }
        }
    }
}