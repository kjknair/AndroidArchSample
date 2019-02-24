package com.sensehawk.data.store

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.sensehawk.data.model.ProjectEntity
import com.sensehawk.data.repository.ProjectsRemote
import com.sensehawk.data.test.factory.DataFactory
import com.sensehawk.data.test.factory.ProjectFactory
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class ProjectsRemoteDataStoreTest {

    private val remote = mock<ProjectsRemote>()
    private val store: ProjectsRemoteDataStore = ProjectsRemoteDataStore(remote)

    @Before
    fun setUp() {

    }

    @Test
    fun getProjectsCompletes() {
        stubRemoteGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        val testObserver = store.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnsData() {
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubRemoteGetProjects(Observable.just(data))
        val testObserver = store.getProjects().test()
        testObserver.assertValue(data)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveProjectsThrowsException() {
        store.saveProjects(listOf(ProjectFactory.makeProjectEntity())).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearProjectsThrowsException() {
        store.clearProjects().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getBookmarkedProjectsThrowsException() {
        store.getBookmarkedProjects().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setProjectAsBookmarkThrowsException() {
        store.setProjectAsBookmark(DataFactory.randomString()).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setProjectAsNotBookmarkedThrowsException() {
        store.setProjectAsNotBookmarked(DataFactory.randomString()).test()
    }

    private fun stubRemoteGetProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(remote.getProjects()).thenReturn(observable)
    }

}