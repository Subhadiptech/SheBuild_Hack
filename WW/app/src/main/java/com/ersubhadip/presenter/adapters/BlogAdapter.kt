package com.ersubhadip.presenter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ersubhadip.domains.dto.adapterModels.BlogModel
import com.ersubhadip.ww.R
import javax.inject.Inject

class BlogAdapter @Inject constructor() :
    ListAdapter<BlogModel, BlogAdapter.BlogViewHolder>(DiffUtilsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.campaingns_item_layout, parent, false)
        return BlogViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindData(item)
    }

    class DiffUtilsCallback : DiffUtil.ItemCallback<BlogModel>() {
        override fun areItemsTheSame(oldItem: BlogModel, newItem: BlogModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BlogModel, newItem: BlogModel): Boolean {

            return oldItem == newItem
        }

    }

    inner class BlogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //view init here

        fun bindData(item: BlogModel) {
            //binding here

            //intent to click read more

        }
    }
}