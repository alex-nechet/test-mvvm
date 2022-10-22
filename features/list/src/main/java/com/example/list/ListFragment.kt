package com.example.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex.android.git.interactor.model.State
import com.alex.android.git.interactor.model.User
import com.example.list.databinding.FragmentListBinding
import com.example.shared.navigation.Destination
import com.example.shared.navigation.navigateTo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalPagingApi
class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModel()

    private lateinit var binding: FragmentListBinding

    private val userAdapter = UserListAdapter { navigateTo(Destination.Details(it.id)) }
    private val adapter =
        userAdapter.withLoadStateFooter(UserListLoadAdapter { userAdapter.retry() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            list.layoutManager = LinearLayoutManager(context)
            list.adapter = adapter
        }
        if (savedInstanceState == null) { viewModel.fetchData() }
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.data.collectLatest { state ->
                handleUIState(state)
                setupData(state)
            }
        }
    }

    private suspend fun setupData(state: State<PagingData<User>>) {
        with(binding) {
            when (state) {
                is State.Error -> error.errorText.text = state.msg.orEmpty()
                is State.Loading -> error.errorText.text = ""
                is State.Success -> {
                    error.errorText.text = ""
                    state.data?.let { userAdapter.submitData(it) }
                }
            }
        }
    }

    private fun handleUIState(state: State<PagingData<User>>) {
        with(binding) {
            progress.isVisible = state is State.Loading
            error.errorText.isVisible = state is State.Error
            list.isVisible = state is State.Success
        }
    }
}
