package com.sensehawk.data.test.factory

import com.sensehawk.data.model.ProjectEntity
import com.sensehawk.domain.model.Project

object ProjectFactory {

    fun makeProjectEntity():ProjectEntity {
        return ProjectEntity(
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomBoolean()
        )
    }

    fun makeProject(): Project{
        return Project(
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomBoolean()
        )
    }

}
