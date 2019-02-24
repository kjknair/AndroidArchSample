package com.sensehawk.mobile_ui.browse

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sensehawk.mobile_ui.R
import com.sensehawk.mobile_ui.model.Project
import javax.inject.Inject

class BrowseAdapter @Inject constructor() : RecyclerView.Adapter<BrowseAdapter.ViewHolder>() {

    var projects: List<Project> = listOf()
    var projectListener: ProjectListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return projects.size * 1000
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val project = projects[position % projects.size]
        holder.bind(project)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var avatarImage: ImageView
        var ownerNameText: TextView
        var projectNameText: TextView
        var bookmarkedImage: ImageView

        init {
            avatarImage = itemView.findViewById(R.id.image_owner_avatar)
            ownerNameText = itemView.findViewById(R.id.text_owner_name)
            projectNameText = itemView.findViewById(R.id.text_project_name)
            bookmarkedImage = itemView.findViewById(R.id.image_bookmarked)
        }

        fun bind(project: Project) {
            ownerNameText.text = project.ownerName
            projectNameText.text = project.fullName

            Glide.with(itemView.context)
                .load(project.ownerAvatar)
                .apply(RequestOptions().circleCrop())
                .into(avatarImage)
            val startResource = if (project.isBookmarked) {
                R.drawable.ic_star_black_24dp
            } else {
                R.drawable.ic_star_border_black_24dp
            }
            bookmarkedImage.setImageResource(startResource)
            itemView.setOnClickListener {
                if (project.isBookmarked) {
                    projectListener?.onBookmarkedProjectClicked(project.id)
                } else {
                    projectListener?.onProjectClicked(project.id)
                }
            }
        }

    }

}