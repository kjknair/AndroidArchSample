package com.sensehawk.remote.mapper

import com.sensehawk.data.model.ProjectEntity
import com.sensehawk.remote.model.ProjectModel
import javax.inject.Inject

open class ProjectResponseModelMapper @Inject constructor() : ModelMapper<ProjectModel, ProjectEntity> {
    override fun mapFromModel(model: ProjectModel): ProjectEntity {
        return ProjectEntity(
            model.id,
            model.name,
            model.fullName,
            model.starCount,
            model.dateCreated,
            model.owner.ownerName,
            model.owner.ownerAvatar
        )
    }
}