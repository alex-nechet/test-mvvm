package com.example.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.details.databinding.ItemDetailsBinding


class UserDetailsAdapter : ListAdapter<Pair<Int, String>,
        UserDetailsAdapter.UserViewHolder>(DetailsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_details, parent, false)
        val binding = ItemDetailsBinding.bind(view)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    class UserViewHolder(
        private val binding: ItemDetailsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Pair<Int, String>) {
            with(binding) {
                header.text = binding.root.context.getText(user.first)
                text.text = user.second
            }
        }
    }

    class DetailsDiffCallback : DiffUtil.ItemCallback<Pair<Int, String>>() {
        override fun areItemsTheSame(
            oldItem: Pair<Int, String>,
            newItem: Pair<Int, String>
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Pair<Int, String>,
            newItem: Pair<Int, String>
        ) = oldItem == newItem
    }
}
