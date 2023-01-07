package com.ersubhadip.presenter.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ersubhadip.domains.dto.adapterModels.NGOModel
import com.ersubhadip.ww.R
import javax.inject.Inject


class NGOAdapter @Inject constructor() :
    ListAdapter<NGOModel, NGOAdapter.NGOViewHolder>(DiffUtilsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NGOViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.campaingns_item_layout, parent, false)
        return NGOViewHolder(view)
    }

    override fun onBindViewHolder(holder: NGOViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindData(item)
    }

    class DiffUtilsCallback : DiffUtil.ItemCallback<NGOModel>() {
        override fun areItemsTheSame(oldItem: NGOModel, newItem: NGOModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NGOModel, newItem: NGOModel): Boolean {

            return oldItem == newItem
        }

    }

    inner class NGOViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //view init here
        val title: TextView = itemView.findViewById(R.id.campaigns_title)
        val desc: TextView = itemView.findViewById(R.id.campaigns_description)
        val link: TextView = itemView.findViewById(R.id.read_more)
        fun bindData(item: NGOModel) {
            //binding here
            title.text = item.title
            desc.text = item.desc
            //intent to click read more
            link.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(item.link)
                itemView.context.startActivity(i)
            }
        }
    }
}