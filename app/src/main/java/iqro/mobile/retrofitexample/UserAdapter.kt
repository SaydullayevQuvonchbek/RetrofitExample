package iqro.mobile.retrofitexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import iqro.mobile.retrofitexample.databinding.ItemTodoBinding
import iqro.mobile.retrofitexample.databinding.ItemUserBinding

class UserDiffUtils():DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem==newItem
    }
}

class UserAdapter:PagingDataAdapter<User,UserAdapter.UserViewHolder>(UserDiffUtils()) {
    class UserViewHolder(val binding: ItemUserBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(user:User?){
binding.usernametv.text=user?.getFullName()
            Glide.with(binding.root.context).load(user?.avatar).into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}