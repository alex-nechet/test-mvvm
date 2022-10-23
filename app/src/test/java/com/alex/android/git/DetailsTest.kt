package com.alex.android.git

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.AllUsersInteractor
import com.example.domain.UserDetailsInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.kotlin.whenever
import org.mockito.runners.MockitoJUnitRunner
import kotlin.test.*


private const val MOCK_MOVIE_ID = 100L
private const val TEST_MESSAGE = "Test"

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailsTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var mockedInteractor: UserDetailsInteractor

    lateinit var viewModel: com.example.details.DetailViewModel

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        viewModel = com.example.details.DetailViewModel(mockedInteractor, MOCK_MOVIE_ID)
    }

//    @Test
//    fun `if interactor returns success and null data should not crash and no error or loading state displayed`() =
//        runBlocking {
//            whenever(mockedInteractor.getAdvancedUserDetails(MOCK_MOVIE_ID))
//                .thenReturn(
//                    flow { emit(State.Success<OtherInfo>(null)) }
//                )
//
//            viewModel.fetchAdvancedDetails()
//            viewModel.error.observeForever { assertFalse(it) }
//            viewModel.loading.observeForever { assertFalse(it) }
//        }
//
//
//    @Test
//    fun `if interactor returns error should have error state`() = runBlockingTest {
//        whenever(mockedInteractor.getAdvancedUserDetails(MOCK_MOVIE_ID))
//            .thenReturn(
//                flow { emit(State.Error<OtherInfo>(TEST_MESSAGE)) }
//            )
//        viewModel.fetchAdvancedDetails()
//        viewModel.error.observeForever { assert(it) }
//        viewModel.errorMessage.observeForever {
//            assertEquals(TEST_MESSAGE, it)
//        }
//        viewModel.loading.observeForever { assertFalse(it) }
//    }
//
//    @Test
//    fun `if iteractor returns loading should have loading state`() = runBlockingTest {
//        whenever(mockedInteractor.getAdvancedUserDetails(MOCK_MOVIE_ID))
//            .thenReturn(
//                flow { emit(State.Loading<OtherInfo>()) }
//            )
//        viewModel.fetchAdvancedDetails()
//        viewModel.error.observeForever { assertFalse { it } }
//        viewModel.loading.observeForever { assert(it) }
//    }


}