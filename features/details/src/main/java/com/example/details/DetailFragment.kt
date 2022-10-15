package com.example.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.shared.binding.setImageUrl
import com.alex.android.git.interactor.model.State
import com.alex.android.git.interactor.model.BriefInfo
import com.example.details.databinding.FragmentDetailBinding
import com.example.domain.model.OtherInfo
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModel { parametersOf(args.movieId) }

    private lateinit var binding: FragmentDetailBinding

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
        binding.lifecycleOwner = viewLifecycleOwner
        if (savedInstanceState == null) {
            fetchData()
        }
    }

    private fun fetchData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.fetchHeaderDetails().collectLatest { setBasicDetails(it) }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.fetchAdvancedDetails().collectLatest { setAdvancedDetails(it) }
        }
    }


    private fun setBasicDetails(data: BriefInfo) = with(binding) {
        url.text = data.url
        title.text = data.login
        headerImage.setImageUrl(data.avatarUrl)
    }

    private fun setAdvancedDetails(state: State<OtherInfo>) = with(binding) {
        header.isVisible = state !is State.Loading
        detailsContainer.isVisible = state !is State.Loading
        error.errorText.isVisible = state is State.Error
        loading.isVisible = state is State.Loading

        when (state) {
            is State.Error -> error.errorText.text = state.msg.orEmpty()
            is State.Loading -> error.errorText.text = ""
            is State.Success -> {
                error.errorText.text = ""
                val data = state.data
                detailsContainer.isVisible = data != null
                //using databinding here just to display that i can work with it too
                bodyInfo = data
            }
        }
    }
}
