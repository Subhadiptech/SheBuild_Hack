package com.ersubhadip.presenter.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ersubhadip.domains.dto.adapterModels.BlogModel
import com.ersubhadip.domains.dto.adapterModels.ExploreModel
import com.ersubhadip.ww.R
import javax.inject.Inject

class ExploreAdapter @Inject constructor() :
    ListAdapter<ExploreModel, ExploreAdapter.ExploreViewHolder>(DiffUtilsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.explore_item_layout, parent, false)
        return ExploreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindData(item)
    }

    class DiffUtilsCallback : DiffUtil.ItemCallback<ExploreModel>() {
        override fun areItemsTheSame(oldItem: ExploreModel, newItem: ExploreModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ExploreModel, newItem: ExploreModel): Boolean {

            return oldItem == newItem
        }

    }

    inner class ExploreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //view init here
        val title: TextView = itemView.findViewById(R.id.explore_title)
        val desc: TextView = itemView.findViewById(R.id.explore_description)
        val apply: Button = itemView.findViewById(R.id.apply_btn)

        fun bindData(item: ExploreModel) {
            //binding here
            title.text = item.exploreTitle
            desc.text = item.exploreDesc

            //intent to click read more
            apply.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(item.applyUrl)
                itemView.context.startActivity(i)
            }

        }
    }
}
