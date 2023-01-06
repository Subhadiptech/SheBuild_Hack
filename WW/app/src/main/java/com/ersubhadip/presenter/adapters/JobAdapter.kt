package com.ersubhadip.presenter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ersubhadip.domains.dto.adapterModels.JobModel
import com.ersubhadip.ww.R
import javax.inject.Inject


class JobAdapter @Inject constructor() :
    ListAdapter<JobModel, JobAdapter.JobViewHolder>(DiffUtilsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.jobs_item_layout, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindData(item)
    }

    class DiffUtilsCallback : DiffUtil.ItemCallback<JobModel>() {
        override fun areItemsTheSame(oldItem: JobModel, newItem: JobModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: JobModel, newItem: JobModel): Boolean {

            return oldItem == newItem
        }

    }

    inner class JobViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //view init here
        val title: TextView = itemView.findViewById(R.id.jobs_title)
        val desc: TextView = itemView.findViewById(R.id.jobs_description)
        val link: TextView = itemView.findViewById(R.id.know_more)

        fun bindData(item: JobModel) {
            //binding here
            title.text = item.jobTitle
            desc.text = item.jobDesc

            //intent to click read more
            link.setOnClickListener {
                //todo:Redirect
            }

        }
    }
}