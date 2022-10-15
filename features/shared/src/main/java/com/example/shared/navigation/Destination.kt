package com.example.shared.navigation

import android.net.Uri
import androidx.core.net.toUri

sealed class Destination(val uri : Uri) {
    data class Details(val movieId: Long) : Destination("androidApp://details/${movieId}".toUri())
}