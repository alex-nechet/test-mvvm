package com.example.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.User
import com.example.list.databinding.FragmentListBinding
import com.example.shared.extensions.launchOnEveryStart
import com.example.shared.navigation.Destination
import com.example.shared.navigation.navigateTo
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalPagingApi
class ListFragment : Fragment() {
    private val viewModel: ListViewModel by viewModel()

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = _binding!!

    private val userAdapter = UserListAdapter { navigateTo(Destination.Details(it.id)) }
    private val loadStateFooter = UserListLoadAdapter { userAdapter.retry() }
    private val adapter = userAdapter.withLoadStateFooter(
        footer = loadStateFooter
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            list.layoutManager = LinearLayoutManager(context)
            list.adapter = adapter
        }

        userAdapter.addLoadStateListener {
            loadStateFooter.loadState =  it.refresh
        }
        observeData()
    }

    private fun observeData() {
        launchOnEveryStart(viewModel.data) {

            userAdapter.submitData(this.lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
