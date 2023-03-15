package com.example.users.repository

import com.example.domain.repository.UserRepository
import com.example.local.users.UserLocalDataSource
import com.example.remote.users.UserRemoteDataSource
import com.example.remote.users.model.UserDto
import com.example.users.mappers.toDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class UsersRepositoryImplTest {
    private val userRemoteDataSource: UserRemoteDataSource = mock()
    private val userLocalDataSource: UserLocalDataSource = mock()
    private val coroutineContext = UnconfinedTestDispatcher()
    private val userId = 10L
    private val userDto = UserDto(
        id = userId,
        login = null,
        location = null,
        followingUrl = null,
        gravatarId = null,
        gistsUrl = null,
        organizationsUrl = null,
        followersUrl = null,
        subscriptionsUrl = null,
        starredUrl = null,
        receivedEventsUrl = null,
        nodeId = null,
        eventsUrl = null,
        type = null,
        updatedAt = null,
        url = null,
        avatarUrl = null,
        reposUrl = null,
        htmlUrl = null,
        blog = null,
        createdAt = null,
        bio = "",
        company = "",
        email = "",
        followers = 0,
        following = 0,
        name = "",
        publicGists = 0,
        publicRepos = 0,
        siteAdmin = false,
        twitterUsername = ""
    )
    lateinit var repository: UserRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineContext)

        repository = UsersRepositoryImpl(
            remote = userRemoteDataSource,
            local = userLocalDataSource,
            coroutineContext = coroutineContext
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch user remote success`() = runTest {
        whenever(userLocalDataSource.getUserDetails(userId)).thenReturn(null)
        whenever(userRemoteDataSource.getDetails(userId)).thenReturn(Result.success(userDto))

        val response = repository.fetchUser(userId)

        assertTrue { response.isSuccess }
        assertEquals(response.getOrNull()?.id, userId)
    }

    @Test
    fun `fetch user local success`() = runTest {
        val user = userDto.toDb()

        whenever(userLocalDataSource.getUserDetails(userId)).thenReturn(user)
        whenever(userRemoteDataSource.getDetails(userId)).thenReturn(Result.success(null))

        val response = repository.fetchUser(userId)

        assertTrue { response.isSuccess }
        assertEquals(response.getOrNull()?.id, userId)
    }

    @Test
    fun `fetch user remote error`() = runTest {
        val userId = 10L
        val exception = Exception("Unknown exception")

        whenever(userLocalDataSource.getUserDetails(userId)).thenReturn(null)
        whenever(userRemoteDataSource.getDetails(userId)).thenReturn(Result.failure(exception))

        val response = repository.fetchUser(userId)

        assertTrue { response.isFailure }
        response.onFailure { assertEquals(it, exception) }
    }
}
