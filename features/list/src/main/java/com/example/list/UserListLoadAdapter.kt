package com.example.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.view.progress
import kotlinx.android.synthetic.main.item_load_state.view.*

class UserListLoadAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<UserListLoadAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        val progress = holder.itemView.progress
        val btnRetry = holder.itemView.retry
        val txtErrorMessage = holder.itemView.errorMessage

        btnRetry.isVisible = loadState !is LoadState.Loading
        txtErrorMessage.isVisible = loadState !is LoadState.Loading
        progress.isVisible = loadState is LoadState.Loading

        if (loadState is LoadState.Error){
            txtErrorMessage.text = loadState.error.localizedMessage
        }

        btnRetry.setOnClickListener { retry.invoke() }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_load_state, parent, false)
        )
    }

    class LoadStateViewHolder(view: View) : RecyclerView.ViewHolder(view)
}