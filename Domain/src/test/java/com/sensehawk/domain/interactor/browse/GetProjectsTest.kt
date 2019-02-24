package com.sensehawk.domain.interactor.browse

import com.nhaarman.mockito_kotlin.whenever
import com.sensehawk.domain.executor.PostExecutionThread
import com.sensehawk.domain.model.Project
import com.sensehawk.domain.repository.ProjectRepository
import com.sensehawk.domain.test.ProjectDataFactory
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetProjectsTest{

    private lateinit var getProjects: GetProjects
    @Mock lateinit var projectRepository: ProjectRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this);
        getProjects = GetProjects(projectRepository, postExecutionThread)
    }

    @Test
    fun getProjectCompletes() {
        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))
        val testObserver = getProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectReturnsData() {
        val projects = ProjectDataFactory.makeProjectList(2)
        stubGetProjects(Observable.just(projects))
        val testObserver = getProjects.buildUseCaseObservable().test()
        testObserver.assertValue(projects)
    }

    private fun stubGetProjects(observable: Observable<List<Project>>) {
        whenever(projectRepository.getProjects()).thenReturn(observable)
    }

}