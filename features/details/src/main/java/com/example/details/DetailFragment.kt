package com.example.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.details.databinding.FragmentDetailBinding
import com.example.details.mappers.toErrorResource
import com.example.domain.common.model.State
import com.example.domain.common.model.map
import com.example.domain.model.Data
import com.example.domain.model.User
import com.example.domain.model.UserDetails
import com.example.shared.extensions.launchOnEveryStart
import com.example.shared.extensions.setImageUrl
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModel { parametersOf(args.movieId) }

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = _binding!!

    private val adapter = UserDetailsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.detailsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.detailsRecyclerView.adapter = adapter
        observeData()
    }

    private fun observeData() {
        launchOnEveryStart(viewModel.userDetails) { setDetails(it) }
    }

    private fun setBasicDetails(data: User) = with(binding) {
        url.text = data.url
        title.text = data.login
        headerImage.setImageUrl(data.avatarUrl)
    }

    private fun setFooterDetails(data: List<Data>) = adapter.submitList(data)

    private fun setDetails(state: State<UserDetails>) {
        with(binding) {
            loading.isVisible = state is State.Loading
            when (state) {
                is State.Error -> {
                    error.errorText.text = getString(state.errorType.toErrorResource())
                }
                else -> error.errorText.text = ""
            }
            if (state is State.Success) {
                setBasicDetails(state.data.headerInfo)
                setFooterDetails(state.data.footerInfo)
                handleUIState(state.map { it.footerInfo })
            }
        }
    }

    private fun FragmentDetailBinding.handleUIState(state: State<List<Data>>) {
        detailsRecyclerView.isVisible = state is State.Success
        loading.isVisible = state is State.Loading
        error.errorText.isVisible = state is State.Error
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
