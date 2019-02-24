package com.sensehawk.mobile_ui.bookmarked

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

class BookmarkedAdapter @Inject constructor(): RecyclerView.Adapter<BookmarkedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bookmarked_project, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return projects.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects[position]
        holder.bind(project)
    }

    var projects : List<Project> = emptyList()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var avatarImage: ImageView
        var ownerNameText: TextView
        var projectNameText: TextView

        init {
            avatarImage = itemView.findViewById(R.id.image_owner_avatar)
            ownerNameText = itemView.findViewById(R.id.text_owner_name)
            projectNameText = itemView.findViewById(R.id.text_project_name)
        }

        fun bind(project: Project) {
            ownerNameText.text = project.ownerName
            projectNameText.text = project.fullName

            Glide.with(itemView.context)
                .load(project.ownerAvatar)
                .apply(RequestOptions().circleCrop())
                .into(avatarImage)
        }

    }

}