package com.alex.android.git.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alex.android.git.R
import com.alex.android.git.databinding.ItemListBinding
import com.alex.android.git.interactor.model.BriefInfo
import com.alex.android.git.interactor.model.User
import com.example.domain.converters.toBriefInfo


class UserListAdapter(
    private val listener: OnItemClickListener?
) : PagingDataAdapter<User, UserListAdapter.UserViewHolder>(MovieDiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(movie: BriefInfo?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_list, parent, false)
        val binding = ItemListBinding.bind(view)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val movie = getItem(position) ?: return
        holder.bind(movie.toBriefInfo(), listener)
    }

    class UserViewHolder(
        private val binding: ItemListBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(user: BriefInfo, listener: OnItemClickListener?) {
            binding.item = user
            itemView.setOnClickListener {
                // Triggers click upwards to the adapter on click
                if (listener != null) {
                    if (layoutPosition != RecyclerView.NO_POSITION) {
                        listener.onItemClick(user)
                    }
                }
            }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}
