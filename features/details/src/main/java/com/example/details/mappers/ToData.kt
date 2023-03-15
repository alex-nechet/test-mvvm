package com.example.details.mappers

import com.example.details.R
import com.example.domain.entity.Data
import com.example.domain.entity.User

fun User.toData() = listOf(
    Data(R.string.company, this.company),
    Data(R.string.location, this.location),
    Data(R.string.email, this.email),
    Data(R.string.about_me, this.bio),
    Data(R.string.twitter, this.twitterUsername),
    Data(R.string.followers, this.followers.toString()),
    Data(R.string.following, this.following.toString())
).filter { it.text.isNotEmpty() }
