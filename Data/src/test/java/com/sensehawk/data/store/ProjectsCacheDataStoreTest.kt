package com.sensehawk.data.store

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.sensehawk.data.model.ProjectEntity
import com.sensehawk.data.repository.ProjectsCache
import com.sensehawk.data.test.factory.DataFactory
import com.sensehawk.data.test.factory.ProjectFactory
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Test

class ProjectsCacheDataStoreTest {

    private val cache = mock<ProjectsCache>()
    private val store = ProjectsCacheDataStore(cache)

    @Test
    fun getProjectsComplete() {
        stubGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        val testObserver = store.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnsData() {
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubGetProjects(Observable.just(data))
        val testObserver = store.getProjects().test()
        testObserver.assertValue(data)
    }

    @Test
    fun getProjectCallsCacheSource() {
        stubGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        store.getProjects().test()
        verify(cache).getProjects()
    }


    @Test
    fun saveProjectsComplete() {
        stubSaveProjects(Completable.complete())
        stubLastCacheTime(Completable.complete())
        val projectList = listOf(ProjectFactory.makeProjectEntity())
        val testObserver = store.saveProjects(projectList).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveProjectsCallsCacheSource() {
        stubSaveProjects(Completable.complete())
        stubLastCacheTime(Completable.complete())
        val projectList = listOf(ProjectFactory.makeProjectEntity())
        store.saveProjects(projectList).test()
        verify(cache).saveProjects(projectList)
    }


    @Test
    fun clearProjectsComplete() {
        stubClearCache(Completable.complete())
        val projectList = getProjectEntityList()
        val testObserver = store.clearProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedProjectsComplete() {
        val projectList = getProjectEntityList()
        stubBookmarkedProjects(Observable.just(projectList))
        val testObserver = store.getBookmarkedProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedProjectsReturnsData() {
        val projectList = getProjectEntityList()
        stubBookmarkedProjects(Observable.just(projectList))
        val testObserver = store.getBookmarkedProjects().test()
        testObserver.assertValue(projectList)
    }

    @Test
    fun setProjectAsBookmarkComplete() {
        stubSetProjectAsBookmarked(Completable.complete())
        val testObserver = store.setProjectAsBookmark(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun setProjectAsNotBookmarkComplete() {
        stubSetProjectAsUnBookmarked(Completable.complete())
        val testObserver = store.setProjectAsNotBookmarked(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    private fun getProjectEntityList(): List<ProjectEntity> {
        return listOf(ProjectFactory.makeProjectEntity())
    }

    private fun stubGetProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(cache.getProjects()).thenReturn(observable)
    }

    private fun stubSaveProjects(completable: Completable) {
        whenever(cache.saveProjects(any())).thenReturn(completable)
    }

    private fun stubLastCacheTime(completable: Completable) {
        whenever(cache.setLastCacheTime(any())).thenReturn(completable)
    }

    private fun stubClearCache(completable: Completable) {
        whenever(cache.clearCache()).thenReturn(completable)
    }

    private fun stubBookmarkedProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(cache.getBookmarkedProjects()).thenReturn(observable)
    }

    private fun stubSetProjectAsBookmarked(completable: Completable) {
        whenever(cache.setProjectAsBookmarked(any())).thenReturn(completable)
    }

    private fun stubSetProjectAsUnBookmarked(completable: Completable) {
        whenever(cache.setProjectAsNotBookmarked(any())).thenReturn(completable)
    }

}