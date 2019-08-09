package com.demo.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import androidx.annotation.Nullable


abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var mContentView: View
    protected lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        mActivity = this
        super.onCreate(savedInstanceState)
        setRootLayout(bindLayout())
        initView(savedInstanceState, mContentView)
        doBusiness()
    }

    @SuppressLint("ResourceType")
    open fun setRootLayout(@LayoutRes layoutId: Int) {
        if (layoutId <= 0) return
        mContentView = LayoutInflater.from(this).inflate(layoutId, null)
        setContentView(mContentView)
    }

    abstract fun bindLayout(): Int

    abstract fun initView(@Nullable savedInstanceState: Bundle?, @Nullable contentView: View)

    abstract fun doBusiness()
}