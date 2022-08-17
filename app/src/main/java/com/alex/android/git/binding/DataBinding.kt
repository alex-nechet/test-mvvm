package com.alex.android.git.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.alex.android.git.R


@BindingAdapter(value = ["visibility"])
fun View.setVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["src", "centerCrop"], requireAll = false)
fun ImageView.setImageUrl(imageUrl: String?, centerCrop: Boolean=false) {
    this.load(imageUrl) {
        crossfade(true)
        placeholder(R.drawable.ic_baseline_image_not_supported_24)
        if (centerCrop)
            transformations(CircleCropTransformation())
    }
}
