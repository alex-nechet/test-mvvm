package com.example.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shared.extensions.setImageUrl
import com.alex.android.git.interactor.model.State
import com.example.domain.model.BriefInfo
import com.example.details.databinding.FragmentDetailBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModel { parametersOf(args.movieId) }

    private lateinit var binding: FragmentDetailBinding

    private val adapter = UserDetailsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.detailsRecyclerView.adapter = adapter
        if (savedInstanceState == null) {
            viewModel.fetchData()
        }
        observeData()
    }

    private fun observeData() {
        with(viewLifecycleOwner.lifecycleScope) {
            launch {
                viewModel.headerData.collectLatest { data -> data?.let { setBasicDetails(it) } }
            }
            launch {
                viewModel.footerData.collectLatest {
                    binding.handleUIState(it)
                    setAdvancedDetails(it)
                }
            }
        }
    }

    private fun setBasicDetails(data: BriefInfo) = with(binding) {
        url.text = data.url
        title.text = data.login
        headerImage.setImageUrl(data.avatarUrl)
    }

    private fun setAdvancedDetails(state: State<List<Data>>) {
        with(binding) {
            when (state) {
                is State.Error -> error.errorText.text = state.msg.orEmpty()
                else -> error.errorText.text = ""
            }
            if (state is State.Success) adapter.submitList(state.data)
        }
    }

    private fun FragmentDetailBinding.handleUIState(state: State<List<Data>>) {
        detailsRecyclerView.isVisible = state is State.Success
        loading.isVisible = state is State.Loading
        error.errorText.isVisible = state is State.Error
    }

}
