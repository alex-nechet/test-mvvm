package com.example.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.BriefInfo
import com.example.list.databinding.ItemListBinding
import com.example.shared.extensions.setImageUrl

class UserListAdapter(
    private val action: (info: BriefInfo) -> Unit
) : PagingDataAdapter<BriefInfo, UserListAdapter.UserViewHolder>(UsersDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_list, parent, false)
        val binding = ItemListBinding.bind(view)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val info = getItem(position) ?: return
        holder.bind(info, action)
    }

    class UserViewHolder(
        private val binding: ItemListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: BriefInfo, action: (movie: BriefInfo) -> Unit) {
            with(binding) {
                binding.title.text = user.login
                binding.image.setImageUrl(imageUrl = user.avatarUrl, centerCrop = true)
                itemView.setOnClickListener {
                    // Triggers click upwards to the adapter on click
                    if (layoutPosition != RecyclerView.NO_POSITION) {
                        action.invoke(user)
                    }
                }
            }
        }
    }

    class UsersDiffCallback : DiffUtil.ItemCallback<BriefInfo>() {
        override fun areItemsTheSame(oldItem: BriefInfo, newItem: BriefInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BriefInfo, newItem: BriefInfo): Boolean {
            return oldItem == newItem
        }
    }
}
