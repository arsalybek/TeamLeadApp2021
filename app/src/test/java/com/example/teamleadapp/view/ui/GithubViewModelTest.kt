package com.example.teamleadapp.view.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.teamleadapp.data.remote.models.GitHubCommitResponse
import com.example.teamleadapp.data.repository.GithubRepository
import com.example.teamleadapp.di.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.context.startKoin
import org.mockito.MockitoAnnotations
import junit.framework.Assert.assertEquals as assertEquals1

@ExperimentalCoroutinesApi
class GithubViewModelTest {

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            modules(
                listOf(
                    databaseModule,
                    retrofitModule,
                    repositoriesModule,
                    viewModelsModule,
                    appModule
                )
            )
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    // Sets the main coroutines dispatcher to a TestCoroutineScope for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val fakeDataSource = FakeDataSource()

    private lateinit var viewModel: GithubViewModel

    @Before
    fun initViewModel() {
        viewModel = GithubViewModel(fakeDataSource)
    }

    @Test
    fun isLiveDataEmitting_getOrAwaitValue() {
        viewModel.setNewValue("foo")


        assertEquals1(viewModel.liveData1.getOrAwaitValue(), "foo")
        assertEquals1(viewModel.liveData2.getOrAwaitValue(), "FOO")
    }

    @ExperimentalCoroutinesApi
    class FakeDataSource : GithubRepository {

        override suspend fun getUserCommits(
            username: String,
            repoName: String
        ): RequestResult<MutableList<GitHubCommitResponse>> =
            RequestResult.Success<MutableList<GitHubCommitResponse>>(
                mutableListOf()
            )
    }
}