package com.sensehawk.presentation.mapper

import com.sensehawk.domain.model.Project
import com.sensehawk.presentation.model.ProjectView
import javax.inject.Inject

open class ProjectViewMapper @Inject constructor() : Mapper<ProjectView, Project> {
    override fun mapToView(type: Project): ProjectView {
        return with(type) {
            ProjectView(
                id,
                name,
                fullName,
                starCount,
                dateCreated,
                ownerName,
                ownerAvatar,
                isBookmarked
            )
        }
    }

}