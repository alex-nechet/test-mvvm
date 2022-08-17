package com.alex.android.git.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.alex.android.git.data.model.BriefInfo
import com.alex.android.git.databinding.FragmentListBinding
import com.alex.android.git.presentation.ListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalPagingApi
class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModel()

    private lateinit var binding: FragmentListBinding


    private val movieAdapterListener = object : UserListAdapter.OnItemClickListener {
        override fun onItemClick(movie: BriefInfo?) {
            findNavController().navigate(
                ListFragmentDirections.openDetails(
                    movie?.id ?: return
                )
            )
        }
    }

    private val userAdapter = UserListAdapter(movieAdapterListener)
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        if (savedInstanceState == null) {
            viewModel.fetchUsers()
        }

        binding.list.adapter = adapter
        viewModel.data.observe(
            viewLifecycleOwner,
            { data -> userAdapter.submitData(viewLifecycleOwner.lifecycle, data) }
        )
    }
}
