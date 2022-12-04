package com.example.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.details.databinding.ItemDetailsBinding
import com.example.domain.model.Data

class UserDetailsAdapter :
    ListAdapter<Data, UserDetailsAdapter.UserViewHolder>(DetailsDiffCallback()) {

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
        fun bind(data: Data) {
            with(binding) {
                header.text = binding.root.context.getText(data.fieldRes)
                text.text = data.text
            }
        }
    }

    class DetailsDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data) = false
        override fun areContentsTheSame(oldItem: Data, newItem: Data) = false
    }
}
