package com.ersubhadip.presenter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
            .inflate(R.layout.blog_item_layout, parent, false)
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
        val title: TextView = itemView.findViewById(R.id.blog_title)
        val desc: TextView = itemView.findViewById(R.id.blog_description)
        val link: TextView = itemView.findViewById(R.id.read_more)

        fun bindData(item: BlogModel) {
            //binding here
            title.text = item.blogTitle
            desc.text = item.blogDesc

            //intent to click read more
            link.setOnClickListener {
                //todo:Redirect
            }

        }
    }
}