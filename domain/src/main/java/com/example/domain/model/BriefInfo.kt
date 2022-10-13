package com.alex.android.git.interactor.model

data class BriefInfo(
    val login: String,
    val id: Long,
    val avatarUrl: String,
    val url: String
) {
    companion object {
        val EMPTY = BriefInfo(login = "", id = 0L, avatarUrl = "", url = "")
    }
}