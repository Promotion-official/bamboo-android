package com.study.bamboo.adapter.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.R
import com.study.bamboo.databinding.AdminPostAcceptedRecyclerItemBinding
import com.study.bamboo.utils.Admin
import com.study.bamboo.view.fragment.admin.AdminMainFragmentDirections
import com.study.bamboo.view.fragment.admin.dialog.AcceptDialog

// TODO: 2021-08-16 어댑터를 나누자 각각의 상황에맞게


class AdminAcceptAdapter

 :
    PagingDataAdapter<Admin.Accept, AdminAcceptAdapter.AdminAcceptItemViewHolder>(diffCallback) {

    companion object {

        const val TAG = "AdminHomeItemAdapter"
        const val ACCEPTEDType = 0
        const val PENDINGType = 1
        const val REJECTEDType = 2
        const val DELETEDType = 3

        const val ACCEPTED = "ACCEPTED"
        const val PENDING = "PENDING"
        const val DELETED = "DELETED"
        const val REJECTED = "REJECTED"

        private val diffCallback = object : DiffUtil.ItemCallback<Admin.Accept>() {
            override fun areItemsTheSame(oldItem: Admin.Accept, newItem: Admin.Accept): Boolean =

                oldItem == newItem

            override fun areContentsTheSame(oldItem: Admin.Accept, newItem: Admin.Accept): Boolean =
                oldItem == newItem
        }
    }



    //수락
    class AdminAcceptItemViewHolder(val binding: AdminPostAcceptedRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: Admin.Accept) {
            binding.data = data
            binding.executePendingBindings()

        }


        companion object {
            fun from(parent: ViewGroup): AdminAcceptItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: AdminPostAcceptedRecyclerItemBinding = DataBindingUtil
                    .inflate(
                        layoutInflater, R.layout.admin_post_accepted_recycler_item,
                        parent, false
                    )
                return AdminAcceptItemViewHolder(binding)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminAcceptItemViewHolder {


        return AdminAcceptItemViewHolder.from(parent)


    }


    override fun onBindViewHolder(holder: AdminAcceptItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {


            holder.bind(item)
            holder.binding.postMore.setOnClickListener {
                val action =
                    AdminMainFragmentDirections.actionAdminMainFragmentToAcceptDialog(
                        item.id, holder.bindingAdapterPosition,item.title,item.content,item.tag
                    )
                it.findNavController().navigateUp()
                it.findNavController().navigate(action)
            }
        }
    }
}










