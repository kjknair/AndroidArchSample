package com.sensehawk.remote

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.sensehawk.data.model.ProjectEntity
import com.sensehawk.remote.mapper.ProjectResponseModelMapper
import com.sensehawk.remote.model.ProjectModel
import com.sensehawk.remote.model.ProjectsResponseModel
import com.sensehawk.remote.service.GithubTrendingService
import com.sensehawk.remote.test.factory.ProjectDataFactory
import io.reactivex.Observable
import org.junit.Test

class ProjectRemoteImplTest {

    private val mapper = mock<ProjectResponseModelMapper>()
    private val service = mock<GithubTrendingService>()
    private val projectRemoteImpl = ProjectRemoteImpl(service, mapper)

    @Test
    fun getProjectsCompletes() {
        stubServiceSearchRepository(Observable.just(getProjectResponseModel()))
        val testObserver = projectRemoteImpl.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnsData() {
        val data = getProjectResponseModel()
        stubServiceSearchRepository(Observable.just(data))
        val entities = mutableListOf<ProjectEntity>()
        data.items.forEach {
            val projectEntity = ProjectDataFactory.makeProjectEntity()
            entities.add(projectEntity)
            stubMapperMakeFromModel(it, projectEntity)
        }
        val testObserver = projectRemoteImpl.getProjects().test()
        testObserver.assertValues(entities)
    }

    private fun getProjectResponseModel(): ProjectsResponseModel {
        return ProjectDataFactory.makeProjectResponse()
    }

    private fun stubServiceSearchRepository(observable: Observable<ProjectsResponseModel>) {
        whenever(service.searchRepositories(any(), any(), any()))
            .thenReturn(observable)
    }

    private fun stubMapperMakeFromModel(model: ProjectModel, entity: ProjectEntity) {
        whenever(mapper.mapFromModel(model))
            .thenReturn(entity)
    }


}