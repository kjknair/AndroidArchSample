package com.sensehawk.data

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.sensehawk.data.mapper.ProjectMapper
import com.sensehawk.data.model.ProjectEntity
import com.sensehawk.data.repository.ProjectsCache
import com.sensehawk.data.repository.ProjectsDataStore
import com.sensehawk.data.store.ProjectsDataStoreFactory
import com.sensehawk.data.test.factory.DataFactory
import com.sensehawk.data.test.factory.ProjectFactory
import com.sensehawk.domain.model.Project
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class ProjectsDataRepositoryTest {

    private val mapper = mock<ProjectMapper>()
    private val factory = mock<ProjectsDataStoreFactory>()
    private val cache = mock<ProjectsCache>()
    private val store = mock<ProjectsDataStore>()
    private val repository = ProjectsDataRepository(mapper, factory, cache)

    @Before
    fun setUp() {
        stubFactoryGetDataStore()
        stubFactoryGetCacheDataStore()
        stubIsCacheExpired(Single.just(false))
        stubAreProjectsCached(Single.just(false))
        stubCacheSaveProjects(Completable.complete())
    }

    @Test
    fun getProjectCompletes() {
        stubGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        stubMapperMapFromEntity(ProjectFactory.makeProject(), any())
        val testObserver = repository.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectReturnsData() {
        val project = ProjectFactory.makeProject()
        stubGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        stubMapperMapFromEntity(project, any())
        val testObserver = repository.getProjects().test()
        testObserver.assertValue(arrayListOf(project))
    }

    @Test
    fun getBookmarkedProjectCompletes() {
        val project = ProjectFactory.makeProject()
        stubStoreGetBookmarkedProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        stubMapperMapFromEntity(project, any())
        val testObserver = repository.getBookmarkedProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedProjectReturnsData() {
        val project = ProjectFactory.makeProject()
        stubStoreGetBookmarkedProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        stubMapperMapFromEntity(project, any())
        val testObserver = repository.getBookmarkedProjects().test()
        testObserver.assertValue(listOf(project))
    }

    @Test
    fun bookmarkProjectCompletes() {
        stubStoreSetProjectBookmark(Completable.complete())
        val observer = repository.bookmarkProject(DataFactory.randomString()).test()
        observer.assertComplete()
    }

    @Test
    fun unbookmarkProjectCompletes() {
        stubStoreSetProjectNotBookmark(Completable.complete())
        val observer = repository.unbookmarkProject(DataFactory.randomString()).test()
        observer.assertComplete()
    }


    private fun stubStoreSetProjectBookmark(completable: Completable) {
        whenever(store.setProjectAsBookmark(any())).thenReturn(
            completable
        )
    }

    private fun stubStoreSetProjectNotBookmark(completable: Completable) {
        whenever(store.setProjectAsNotBookmarked(any())).thenReturn(
            completable
        )
    }

    private fun stubStoreGetBookmarkedProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(store.getBookmarkedProjects()).thenReturn(observable)
    }

    private fun stubFactoryGetCacheDataStore() {
        whenever(factory.getCacheDataStore())
            .thenReturn(store)
    }

    private fun stubCacheSaveProjects(completable: Completable) {
        whenever(store.saveProjects(any()))
            .thenReturn(completable)
    }

    private fun stubAreProjectsCached(single: Single<Boolean>) {
        whenever(cache.areProjectsCached())
            .thenReturn(single)
    }

    private fun stubIsCacheExpired(single: Single<Boolean>) {
        whenever(cache.isProjectsCacheExpired())
            .thenReturn(single)
    }

    private fun stubMapperMapFromEntity(model: Project, entity: ProjectEntity) {
        whenever(mapper.mapFromEntity(entity))
            .thenReturn(model)
    }

    private fun stubGetProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(store.getProjects())
            .thenReturn(observable)
    }

    private fun stubFactoryGetDataStore() {
        whenever(factory.getDataStore(any(), any()))
            .thenReturn(store)
    }


}