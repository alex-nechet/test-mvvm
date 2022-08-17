package com.alex.android.git.data.model

import com.squareup.moshi.Json

data class OtherInfo(
    val company: String,
    val location: String,
    val email: String,
    val bio: String,
    val twitterUsername: String,
    val followers: String,
    val following: String,
    val createdAt: String
)
