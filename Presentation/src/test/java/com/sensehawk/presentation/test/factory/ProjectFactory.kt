package com.sensehawk.presentation.test.factory

import com.sensehawk.domain.model.Project
import com.sensehawk.presentation.model.ProjectView

object ProjectFactory {

    fun makeProjectView(): ProjectView {
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

    fun makeProject(): Project {
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

    fun makeProjectViewList(count:Int = 2): List<ProjectView>{
        val projectList = mutableListOf<ProjectView>()
        repeat(count){
            projectList.add(makeProjectView())
        }
        return projectList
    }

    fun makeProjectList(count:Int = 2): List<Project>{
        val projectList = mutableListOf<Project>()
        repeat(count){
            projectList.add(makeProject())
        }
        return projectList
    }

}