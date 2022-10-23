package com.example.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.alex.android.git.interactor.model.State
import com.example.domain.UserDetailsInteractor
import com.example.domain.model.BriefInfo
import com.example.domain.model.OtherInfo
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


private const val MOCK_MOVIE_ID = 100L
private const val TEST_MESSAGE = "Test"

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailsTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    lateinit var mockedInteractor: UserDetailsInteractor

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `if interactor returns success and null data should not crash and no error or loading state displayed`() =
        runBlocking {
            whenever(mockedInteractor.getAdvancedUserDetails(MOCK_MOVIE_ID))
                .thenReturn(
                    flow { emit(State.Success<OtherInfo>(null)) }
                )
            val viewModel = prepareViewModel()
            viewModel.fetchData()
            viewModel.headerData
                .onCompletion { println("header done") }
                .onEach { println("header $it") }
                .test {
                    assertEquals(awaitItem(), null)
                    assertEquals(awaitItem(), BriefInfo.EMPTY)
                    cancel()
                }
            viewModel.footerData
                .onCompletion { println("footer done") }
                .onEach { println("footer $it") }
                .test {
                    assertEquals(awaitItem(), State.Success(null))
                    cancel()
                }
        }

    @Test
    fun `if interactor returns error should have error state`() = runBlocking {
        whenever(mockedInteractor.getAdvancedUserDetails(MOCK_MOVIE_ID))
            .thenReturn(
                flow { emit(State.Error<OtherInfo>(TEST_MESSAGE)) }
            )
        val viewModel = prepareViewModel()

        viewModel.fetchData()
        viewModel.headerData
            .onCompletion { println("header done") }
            .onEach { println("header $it") }
            .test {
                assertEquals(awaitItem(), null)
                assertEquals(awaitItem(), BriefInfo.EMPTY)
                cancel()
            }
        viewModel.footerData
            .onCompletion { println("footer done") }
            .onEach { println("footer $it") }
            .test {
                assertEquals(awaitItem(), State.Error(TEST_MESSAGE))
                cancel()
            }
    }

    private fun prepareViewModel(): DetailViewModel {
        whenever(mockedInteractor.getBriefUserDetails(MOCK_MOVIE_ID))
            .thenReturn(
                flow { emit(BriefInfo.EMPTY) }
            )
        val viewModel = DetailViewModel(mockedInteractor, MOCK_MOVIE_ID)
        return viewModel
    }

    @Test
    fun `if iteractor returns loading should have loading state`() = runBlocking {
        whenever(mockedInteractor.getAdvancedUserDetails(MOCK_MOVIE_ID))
            .thenReturn(
                flow { emit(State.Loading<OtherInfo>()) }
            )

        val viewModel = prepareViewModel()

        viewModel.fetchData()
        viewModel.headerData
            .onCompletion { println("header done") }
            .onEach { println("header $it") }
            .test {
                assertEquals(awaitItem(), null)
                assertEquals(awaitItem(), BriefInfo.EMPTY)
                cancel()
            }
        viewModel.footerData
            .onCompletion { println("footer done") }
            .onEach { println("footer $it") }
            .test {
                assert(awaitItem() is State.Loading)
                cancel()
            }
    }


}