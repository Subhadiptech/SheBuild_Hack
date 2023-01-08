package com.ersubhadip.presenter.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ersubhadip.domains.dto.adapterModels.StoryModel
import com.ersubhadip.ww.R
import javax.inject.Inject

class StoryAdapter @Inject constructor() :
    ListAdapter<StoryModel, StoryAdapter.StoryViewHolder>(DiffUtilsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.stories_item_layout, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindData(item)
    }

    class DiffUtilsCallback : DiffUtil.ItemCallback<StoryModel>() {
        override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
            return oldItem == newItem
        }

    }

    inner class StoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //view init here
        val title: TextView = itemView.findViewById(R.id.story_title)
        val tag1: TextView = itemView.findViewById(R.id.tag_1)
        val tag2: TextView = itemView.findViewById(R.id.tag_2)
        val desc: TextView = itemView.findViewById(R.id.story_description)

        fun bindData(item: StoryModel) {
            //binding here
            title.text = item.storyTitle
            desc.text = item.storyDesc
            tag1.text = item.storyTags[0]
            tag2.text = item.storyTags[1]

            itemView.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(item.url)
                itemView.context.startActivity(i)
            }

        }
    }
}
