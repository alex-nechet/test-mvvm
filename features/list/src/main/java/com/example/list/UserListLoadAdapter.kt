package com.example.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.list.databinding.ItemLoadStateBinding

class UserListLoadAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<UserListLoadAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState, retry)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder {
        val binding = ItemLoadStateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadStateViewHolder(binding)
    }

    class LoadStateViewHolder(
        private val binding: ItemLoadStateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState, retryAction: () -> Unit) {
            with(binding) {
                retry.isVisible = loadState !is LoadState.Loading
                errorMessage.isVisible = loadState !is LoadState.Loading
                progress.isVisible = loadState is LoadState.Loading

                if (loadState is LoadState.Error) {
                    errorMessage.text = loadState.error.localizedMessage
                }
                retry.setOnClickListener { retryAction.invoke() }
            }
        }
    }
}