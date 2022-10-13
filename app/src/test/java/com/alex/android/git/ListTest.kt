package com.alex.android.git

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import com.alex.android.git.interactor.AllUsersInteractor
import com.alex.android.git.presentation.ListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.kotlin.whenever
import org.mockito.runners.MockitoJUnitRunner

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ListTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var mockedInteractor: AllUsersInteractor

    lateinit var viewModel: ListViewModel

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ListViewModel(mockedInteractor)
    }

    @Test
    fun `if interactor throws error show error state`() = runBlocking {
        whenever(mockedInteractor.getUsers()).thenReturn(flow { throw java.lang.Exception("test Message") })
        viewModel.fetchUsers()
        viewModel.error.observeForever { assert(it) }
    }
}