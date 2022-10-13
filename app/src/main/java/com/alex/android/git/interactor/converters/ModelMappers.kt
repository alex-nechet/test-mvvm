package com.alex.android.git.interactor.converters


import com.alex.android.git.interactor.model.BriefInfo
import com.alex.android.git.interactor.model.OtherInfo
import com.alex.android.git.interactor.model.User
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

fun UserResponse.toOtherInfo() = OtherInfo(
    company = company.orEmpty(),
    location = location.orEmpty(),
    email = email.orEmpty(),
    bio = bio.orEmpty(),
    twitterUsername = twitterUsername.orEmpty(),
    followers = (followers ?: 0).toString(),
    following = (following ?: 0).toString(),
    createdAt = createdAt.orEmpty()

)
