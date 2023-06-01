package com.example.newsapp.utilities

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.newsapp.R

fun ImageView.setGlide(imageUrl: String) {
    Glide.with(this.context).load(imageUrl).centerCrop().placeholder(R.drawable.ic_image)
        .error(R.drawable.ic_image_not_supported)
        .into(this)
}