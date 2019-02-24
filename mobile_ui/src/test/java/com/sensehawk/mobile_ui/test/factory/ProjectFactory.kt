package com.sensehawk.mobile_ui.test.factory

import com.sensehawk.mobile_ui.model.Project
import com.sensehawk.presentation.model.ProjectView

object ProjectFactory {

    fun makeProjectView(): ProjectView{
        return ProjectView(
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