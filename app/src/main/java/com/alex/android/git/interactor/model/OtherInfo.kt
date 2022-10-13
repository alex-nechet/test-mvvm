package com.alex.android.git.interactor.model


data class OtherInfo(
    val company: String,
    val location: String,
    val email: String,
    val bio: String,
    val twitterUsername: String,
    val followers: String,
    val following: String,
    val createdAt: String
) {
    companion object {
        val EMPTY = OtherInfo(
            company = "",
            location = "",
            email = "",
            bio = "",
            twitterUsername = "",
            followers = "",
            following = "",
            createdAt = ""
        )
    }
}
