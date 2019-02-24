package com.sensehawk.cache

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.sensehawk.cache.db.ProjectsDatabase
import com.sensehawk.cache.mapper.CachedProjectMapper
import com.sensehawk.cache.test.factory.CacheProjectDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

typealias ProjectDataFactory = CacheProjectDataFactory

@RunWith(RobolectricTestRunner::class)
class ProjectCacheImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database: ProjectsDatabase = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application.applicationContext,
        ProjectsDatabase::class.java
    ).allowMainThreadQueries().build()
    private val mapper = CachedProjectMapper()
    private val subject = ProjectCacheImpl(database, mapper)

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun clearCacheCompletes() {
        val testSubscriber = subject.clearCache().test()
        testSubscriber.assertComplete()
    }

    @Test
    fun saveProjectsCompletes() {
        val data = ProjectDataFactory.makeProjectEntity()
        val testObserver = subject.saveProjects(listOf(data)).test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectReturnsData() {
        val data = ProjectDataFactory.makeProjectEntity()
        subject.saveProjects(listOf(data)).test()
        val testObserver = subject.getProjects().test()
        testObserver.assertValue(listOf(data))
    }

    @Test
    fun getBookmarkedProjectReturnsData() {
        val data = arrayListOf(ProjectDataFactory.makeProjectEntity().copy(isBookmarked = true))
        subject.saveProjects(data).test()
        val testObserver = subject.getBookmarkedProjects().test()
        testObserver.assertValue(data)
    }


}