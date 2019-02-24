package com.sensehawk.mobile_ui.mapper

import com.sensehawk.mobile_ui.model.Project
import com.sensehawk.presentation.model.ProjectView
import javax.inject.Inject

class ProjectViewMapper @Inject constructor() : ViewMapper<ProjectView, Project> {
    override fun mapToView(presentation: ProjectView): Project {
        return with(presentation) {
            Project(
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