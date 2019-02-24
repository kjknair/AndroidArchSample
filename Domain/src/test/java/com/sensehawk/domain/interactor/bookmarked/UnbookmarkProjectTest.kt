package com.sensehawk.domain.interactor.bookmarked

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.sensehawk.domain.executor.PostExecutionThread
import com.sensehawk.domain.repository.ProjectRepository
import com.sensehawk.domain.test.ProjectDataFactory
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UnbookmarkProjectTest {

    private lateinit var unbookmarkProject: UnbookmarkProject
    @Mock
    lateinit var projectRepository: ProjectRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        unbookmarkProject = UnbookmarkProject(projectRepository, postExecutionThread)
    }

    @Test
    fun unbookmarkProjectComplete() {
        stubBookmarkProject(Completable.complete())
        val testObserver = unbookmarkProject.buildUseCaseCompletable(
            UnbookmarkProject.Params.forProject(ProjectDataFactory.randomUuid())
        ).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalStateException::class)
    fun unbookmarkProjectThrowsException() {
        unbookmarkProject.buildUseCaseCompletable()
    }


    private fun stubBookmarkProject(value: Completable) {
        whenever(projectRepository.unbookmarkProject(any()))
            .thenReturn(value)
    }


}
