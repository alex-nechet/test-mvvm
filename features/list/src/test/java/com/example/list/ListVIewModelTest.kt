package com.example.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.domain.AllUsersInteractor
import com.example.domain.model.BriefInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals


@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ListVIewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    lateinit var mockedInteractor: AllUsersInteractor

    lateinit var viewModel: ListViewModel

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ListViewModel(mockedInteractor)
    }

    @Test
    fun `if interactor throws error show error state`() = runBlocking {
        whenever(mockedInteractor.invoke()).thenReturn(flow { PagingData.empty<BriefInfo>() })
        viewModel.fetchData()
        viewModel.data
            .onCompletion { println("done") }
            .onEach { println("$it") }
            .test {
                val item = awaitItem()
                assertEquals(item, PagingData.empty())
                cancel()
            }
    }
}