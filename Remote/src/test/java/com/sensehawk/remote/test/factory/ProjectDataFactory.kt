package com.sensehawk.remote.test.factory

import com.sensehawk.data.model.ProjectEntity
import com.sensehawk.remote.model.OwnerModel
import com.sensehawk.remote.model.ProjectModel
import com.sensehawk.remote.model.ProjectsResponseModel

object ProjectDataFactory {

    fun makeOwner(): OwnerModel {
        return OwnerModel(
            DataFactory.randomString(),
            DataFactory.randomString()
        )
    }

    fun makeProject(): ProjectModel {
        return ProjectModel(
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            makeOwner()
        )
    }

    fun makeProjectResponse(count: Int = 2): ProjectsResponseModel {
        val projectsList = mutableListOf<ProjectModel>()
        repeat(count) {
            projectsList.add(makeProject())
        }
        return ProjectsResponseModel(projectsList)
    }

    fun makeProjectEntity(): ProjectEntity {
        return ProjectEntity(
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString()
        )
    }
}