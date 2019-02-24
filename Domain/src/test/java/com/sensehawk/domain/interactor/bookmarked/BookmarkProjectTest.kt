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
import java.lang.IllegalStateException

class BookmarkProjectTest {

    private lateinit var bookmarkProject: BookmarkProject
    @Mock
    lateinit var projectRepository: ProjectRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        bookmarkProject = BookmarkProject(projectRepository, postExecutionThread)
    }

    @Test
    fun bookmarkProjectComplete() {
        stubBookmarkProject(Completable.complete())
        val testObserver = bookmarkProject.buildUseCaseCompletable(
            BookmarkProject.Params.forProject(ProjectDataFactory.randomUuid())
        ).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalStateException::class)
    fun bookmarkProjectThrowsException() {
        bookmarkProject.buildUseCaseCompletable()
    }


    private fun stubBookmarkProject(value: Completable) {
        whenever(projectRepository.bookmarkProject(any()))
            .thenReturn(value)
    }


}
