package com.sensehawk.domain.interactor.bookmarked

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

class GetBookmarkedProjectTest {

    private lateinit var getBookmarkedProjects: GetBookmarkedProjects
    @Mock
    lateinit var projectRepository: ProjectRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread
    
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getBookmarkedProjects = GetBookmarkedProjects(projectRepository, postExecutionThread)
    }

    @Test
    fun getBookmarkedProjectsCompleted() {
        stubGetBookmarkedProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedProjectsReturnsData() {
        val projects = ProjectDataFactory.makeProjectList(2)
        stubGetBookmarkedProjects(Observable.just(projects))
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertValue(projects)
    }

    private fun stubGetBookmarkedProjects(observable: Observable<List<Project>>){
        whenever(projectRepository.getBookmarkedProjects()).thenReturn(observable)
    }

}