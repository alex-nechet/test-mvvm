package com.example.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex.android.git.interactor.model.User
import com.example.list.databinding.FragmentListBinding
import com.example.shared.navigation.Destination
import com.example.shared.navigation.navigateTo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.data.collectLatest { state ->
                setupData(state)
                handleUIState()
            }
        }
    }

    private suspend fun setupData(data: PagingData<User>) {
       userAdapter.submitData(data)
    }

    private fun handleUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            userAdapter.loadStateFlow.collectLatest { loadStates ->
                with(binding) {
                    progress.isVisible = loadStates.refresh is LoadState.Loading
                    list.isVisible = loadStates.refresh !is LoadState.Loading
                    //due to limitation of calls error with the code 403 may happen and this will lead
//                    to error message to pop. Please use api key for proper behaviour. commented out for now
//                    error.errorText.isVisible = loadStates.refresh is LoadState.Error
                }
            }
        }
    }
}
