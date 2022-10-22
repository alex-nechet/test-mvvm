package com.example.shared.extensions

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.shared.R

fun ImageView.setImageUrl(imageUrl: String?, centerCrop: Boolean=false) {
    this.load(imageUrl) {
        crossfade(true)
        placeholder(R.drawable.ic_baseline_image_not_supported_24)
        if (centerCrop)
            transformations(CircleCropTransformation())
    }
}
