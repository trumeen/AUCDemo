package com.demo.feature0.pkg.main

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(src: Any?) {
    Glide.with(this).load(src).into(this)
}
