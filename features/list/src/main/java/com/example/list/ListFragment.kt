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
        if (savedInstanceState == null) {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.fetchUsers().handleStateChanges()
                    .collectLatest { userAdapter.submitData(it) }
            }
        }
    }

    private fun Flow<PagingData<User>>.handleStateChanges() = with(binding) {
        this@handleStateChanges
            .onStart { progress.isVisible = true }
            .catch {
                progress.isVisible = false
                error.errorText.isVisible = true
                error.errorText.text = it.message.orEmpty()
            }
            .onEach {
                progress.isVisible = false
                error.errorText.text = ""
            }
    }
}
