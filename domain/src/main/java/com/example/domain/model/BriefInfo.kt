package com.example.domain.model

data class BriefInfo(
    val login: String,
    val id: Long,
    val avatarUrl: String,
    val url: String
) {
    companion object {
        val EMPTY = BriefInfo(
            login = "",
            id = 0,
            avatarUrl = "",
            url = ""
        )
    }
}