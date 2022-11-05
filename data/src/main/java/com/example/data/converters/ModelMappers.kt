package com.example.data.converters

import com.example.data.db.model.UserDb
import com.example.domain.model.BriefInfo
import com.example.domain.model.OtherInfo
import com.example.network.model.UserResponse

fun UserDb.toBriefInfo() = BriefInfo(
    id = id,
    name = name,
    login = login,
    avatarUrl = avatarUrl,
    url = url
)

fun UserResponse.toOtherInfo() = OtherInfo(
    company = company.orEmpty(),
    location = location.orEmpty(),
    email = email.orEmpty(),
    bio = bio.orEmpty(),
    twitterUsername = twitterUsername.orEmpty(),
    followers = followers.toString(),
    following = following.toString(),
    createdAt = createdAt.orEmpty()

)

fun UserDb.toOtherInfo() = OtherInfo(
    company = company,
    location = location,
    email = email,
    bio = bio,
    twitterUsername = twitterUsername,
    followers = followers.toString(),
    following = following.toString(),
    createdAt = createdAt
)

fun UserResponse.toDb(): UserDb = UserDb(
    login = login.orEmpty(),
    id = id,
    name = name.orEmpty(),
    nodeId = nodeId.orEmpty(),
    avatarUrl = avatarUrl.orEmpty(),
    gravatarId = gravatarId.orEmpty(),
    url = url.orEmpty(),
    htmlUrl = htmlUrl.orEmpty(),
    followersUrl = followersUrl.orEmpty(),
    followingUrl = followingUrl.orEmpty(),
    gistsUrl = gistsUrl.orEmpty(),
    starredUrl = starredUrl.orEmpty(),
    subscriptionsUrl = subscriptionsUrl.orEmpty(),
    organizationsUrl = organizationsUrl.orEmpty(),
    reposUrl = reposUrl.orEmpty(),
    eventsUrl = eventsUrl.orEmpty(),
    receivedEventsUrl = receivedEventsUrl.orEmpty(),
    type = type.orEmpty(),
    siteAdmin = siteAdmin ?: false,
    location = location.orEmpty(),
    company = company.orEmpty(),
    bio = bio.orEmpty(),
    createdAt = createdAt.orEmpty(),
    following = following ?: 0,
    followers = followers ?: 0,
    email = email.orEmpty(),
    twitterUsername = twitterUsername.orEmpty()
)
