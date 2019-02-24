package com.sensehawk.mobile_ui.test.factory

import com.sensehawk.domain.model.Project

object ProjectDataFactory {

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