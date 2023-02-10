package com.example.details

import com.example.domain.UserDetailsUseCase
import com.example.domain.common.model.State
import com.example.domain.entity.User
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class DetailViewModelTest {

    private val userDetailsUseCase: UserDetailsUseCase = mock()
    private val userId = 100L
    private val mockedUser = User(
        login = "login",
        id = userId,
        name = "name",
        nodeId = "nodeId",
        avatarUrl = "avatarUrl",
        gravatarId = "gravaterId",
        url = "url/url",
        htmlUrl = "htmlUrl",
        followersUrl = "followersUrl",
        followingUrl = "followingUr",
        gistsUrl = "gistUrl",
        starredUrl = "starredUrl",
        subscriptionsUrl = "subscriptionUrl",
        organizationsUrl = "organizationsUrl",
        reposUrl = "reposUrl",
        eventsUrl = "eventsUrl",
        receivedEventsUrl = "receivedEventsUrl",
        type = "type",
        siteAdmin = false,
        location = "Kyiv",
        company = "Company",
        email = "john.doe@gmail.com",
        bio = "I am developer",
        twitterUsername = "@username",
        followers = 10,
        following = 10,
        createdAt = "10.10.2010"
    )


    private fun prepareViewModel(userId: Long): DetailViewModel {
        return DetailViewModel(userDetailsUseCase, userId)
    }

    @Test
    fun sfds() = runBlocking{
        whenever(userDetailsUseCase.getUserDetails(userId))
            .thenReturn(flow { emit(State.Success(mockedUser)) })
        val viewModel = prepareViewModel(userId)
       val response =  viewModel.userDetails.await().value
        assertEquals(response, State.Success(mockedUser))
    }
}