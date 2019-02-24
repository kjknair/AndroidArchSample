package com.sensehawk.data.store

import com.nhaarman.mockito_kotlin.mock
import com.sensehawk.data.store.ProjectsCacheDataStore
import com.sensehawk.data.store.ProjectsDataStoreFactory
import com.sensehawk.data.store.ProjectsRemoteDataStore
import org.junit.Test
import kotlin.test.assertEquals

class ProjectDataStoreFactoryTest {

    private val projectCacheStore = mock<ProjectsCacheDataStore>()
    private val projectRemoteStore = mock<ProjectsRemoteDataStore>()
    private val factory = ProjectsDataStoreFactory(projectCacheStore, projectRemoteStore)

    @Test
    fun getDataStoreReturnsRemoteStoreWhenCacheExpired() {
        assertEquals(projectRemoteStore, factory.getDataStore(true, true))
    }

    @Test
    fun getDataStoreReturnsRemoteStoreWhenProjectsNotCached() {
        assertEquals(projectRemoteStore, factory.getDataStore(false, false))
    }

    @Test
    fun getDataStoreReturnsCacheStoreWhenProjectsCached() {
        assertEquals(projectCacheStore, factory.getDataStore(true, false))
    }

    @Test
    fun getCacheDataStoreReturnsCacheDataStore() {
        assertEquals(projectCacheStore, factory.getCacheDataStore())
    }


}
