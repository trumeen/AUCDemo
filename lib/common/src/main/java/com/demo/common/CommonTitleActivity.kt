package com.demo.common

import com.blankj.utilcode.util.BarUtils
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import android.annotation.SuppressLint
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.blankj.utilcode.util.ColorUtils


abstract class CommonTitleActivity : CommonBackActivity() {

    abstract fun bindTitle(): CharSequence

    protected lateinit var commonTitleRootLayout: CoordinatorLayout
    protected lateinit var commonTitleToolbar: Toolbar
    protected lateinit var commonTitleContentView: FrameLayout

    override fun isSwipeBack(): Boolean {
        return true
    }

    @SuppressLint("ResourceType")
    override fun setRootLayout(@LayoutRes layoutId: Int) {
        super.setRootLayout(R.layout.common_activity_title)
        commonTitleRootLayout = findViewById(R.id.commonTitleRootLayout)
        commonTitleToolbar = findViewById(R.id.commonTitleToolbar)
        if (layoutId > 0) {
            commonTitleContentView = findViewById(R.id.commonTitleContentView)
            LayoutInflater.from(this).inflate(layoutId, commonTitleContentView)
        }
        setTitleBar()
        BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.colorPrimary))
        BarUtils.addMarginTopEqualStatusBarHeight(commonTitleRootLayout)
    }

    private fun setTitleBar() {
        setSupportActionBar(commonTitleToolbar)
        val titleBar = supportActionBar
        if (titleBar != null) {
            titleBar.setDisplayHomeAsUpEnabled(true)
            titleBar.title = bindTitle()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}