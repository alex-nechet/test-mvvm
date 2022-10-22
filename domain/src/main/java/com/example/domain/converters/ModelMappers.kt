package com.example.domain.converters


import com.example.domain.model.BriefInfo
import com.example.domain.model.OtherInfo
import com.alex.android.git.interactor.model.User
import com.example.data.db.model.UserDb
import com.example.data.repository.model.UserData

fun UserData.toDb(): UserDb = UserDb(
        login = login,
        id = id,
        nodeId = nodeId,
        avatarUrl = avatarUrl,
        gravatarId = gravatarId,
        url = url,
        htmlUrl = htmlUrl,
        followersUrl = followersUrl,
        followingUrl = followingUrl,
        gistsUrl = gistsUrl,
        starredUrl = starredUrl,
        subscriptionsUrl = subscriptionsUrl,
        organizationsUrl = organizationsUrl,
        reposUrl = reposUrl,
        eventsUrl = eventsUrl,
        receivedEventsUrl = receivedEventsUrl,
        type = type,
        siteAdmin = siteAdmin
    )

fun User.toBriefInfo() = BriefInfo(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    url = url
)

fun UserDb.toBriefInfo() = BriefInfo(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    url = url
)

fun UserData.toOtherInfo() = OtherInfo(
    company = company,
    location = location,
    email = email,
    bio = bio,
    twitterUsername = twitterUsername,
    followers = followers.toString(),
    following = following.toString(),
    createdAt = createdAt

)
