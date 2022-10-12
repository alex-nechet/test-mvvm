package com.example.data.converters

import com.example.data.db.model.UserDb
import com.example.network.model.UserResponse

fun UserResponse.toDb(): UserDb = UserDb(
        login = login.orEmpty(),
        id = id,
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
        siteAdmin = siteAdmin ?: false
    )

