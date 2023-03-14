package com.example.remote.users.mappers

import com.example.network.response.UserResponse
import com.example.remote.users.model.UserDto

fun UserResponse.toDto() = UserDto(
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
    siteAdmin = siteAdmin ?: false,
    name = name.orEmpty(),
    company = company.orEmpty(),
    blog = blog,
    location = location,
    email = email.orEmpty(),
    bio = bio.orEmpty(),
    twitterUsername = twitterUsername.orEmpty(),
    publicRepos = publicRepos ?: 0,
    publicGists = publicGists ?: 0,
    followers = followers ?: 0,
    following = following ?: 0,
    createdAt = createdAt,
    updatedAt = updatedAt
)