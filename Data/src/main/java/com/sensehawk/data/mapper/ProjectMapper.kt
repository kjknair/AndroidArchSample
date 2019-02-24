package com.sensehawk.data.mapper

import com.sensehawk.data.model.ProjectEntity
import com.sensehawk.domain.model.Project
import javax.inject.Inject

open class ProjectMapper @Inject constructor() : EntityMapper<ProjectEntity, Project> {
    public override fun mapFromEntity(entity: ProjectEntity): Project {
        return with(entity) {
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

    public override fun mapToEntity(domain: Project): ProjectEntity {
        return with(domain) {
            ProjectEntity(
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
