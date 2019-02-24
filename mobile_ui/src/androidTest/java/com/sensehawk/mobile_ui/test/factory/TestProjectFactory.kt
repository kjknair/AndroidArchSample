package com.sensehawk.mobile_ui.test.factory

import com.sensehawk.mobile_ui.model.Project
import com.sensehawk.presentation.model.ProjectView

object TestProjectFactory {

    fun makeProjectView(): ProjectView {
        return ProjectView(
            TestDataFactory.randomString(),
            TestDataFactory.randomString(),
            TestDataFactory.randomString(),
            TestDataFactory.randomString(),
            TestDataFactory.randomString(),
            TestDataFactory.randomString(),
            TestDataFactory.randomString(),
            TestDataFactory.randomBoolean()
        )
    }

    fun makeProject(): Project {
        return Project(
            TestDataFactory.randomString(),
            TestDataFactory.randomString(),
            TestDataFactory.randomString(),
            TestDataFactory.randomString(),
            TestDataFactory.randomString(),
            TestDataFactory.randomString(),
            TestDataFactory.randomString(),
            TestDataFactory.randomBoolean()
        )
    }

}