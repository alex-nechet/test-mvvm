package com.example.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.UserBaseInfo
import com.example.list.databinding.ItemListBinding
import com.example.shared.extensions.setImageUrl

class UserListAdapter(
    private val action: (info: UserBaseInfo) -> Unit
) : PagingDataAdapter<UserBaseInfo, UserListAdapter.UserViewHolder>(UsersDiffCallback()) {


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

        fun bind(user: UserBaseInfo, action: (movie: UserBaseInfo) -> Unit) {
            with(binding) {
                title.text = user.name.ifEmpty { user.login }
                image.setImageUrl(imageUrl = user.avatarUrl, centerCrop = true)
                itemView.setOnClickListener {
                    // Triggers click upwards to the adapter on click
                    if (layoutPosition != RecyclerView.NO_POSITION) {
                        action.invoke(user)
                    }
                }
            }
        }
    }

    class UsersDiffCallback : DiffUtil.ItemCallback<UserBaseInfo>() {
        override fun areItemsTheSame(oldItem: UserBaseInfo, newItem: UserBaseInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserBaseInfo, newItem: UserBaseInfo): Boolean {
            return oldItem == newItem
        }
    }
}
