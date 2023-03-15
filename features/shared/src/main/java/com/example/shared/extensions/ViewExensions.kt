package com.example.shared.extensions

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import coil.transform.CircleCropTransformation
import com.example.shared.R
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun ImageView.setImageUrl(imageUrl: String?, centerCrop: Boolean = false) {
    this.load(imageUrl) {
        crossfade(true)
        placeholder(R.drawable.ic_baseline_image_not_supported_24)
        if (centerCrop) {
            transformations(CircleCropTransformation())
        }
    }
}

fun <T> Fragment.launchOnEveryStart(
    deferred: Deferred<StateFlow<T>>,
    collectableAction: suspend (T) -> Unit
) = this.lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
        deferred.await().collectLatest(collectableAction)
    }
}
