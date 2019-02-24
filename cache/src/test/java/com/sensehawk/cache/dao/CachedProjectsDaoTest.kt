package com.sensehawk.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.sensehawk.cache.db.ProjectsDatabase
import com.sensehawk.cache.test.factory.CacheProjectDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CachedProjectsDaoTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application.applicationContext, ProjectsDatabase::class.java
    ).allowMainThreadQueries()
        .build()

    private val dao
        get() = database.cachedProjectsDao()

    @Test
    fun getProjectReturnsData() {
        val cachedProject = CacheProjectDataFactory.makeCacheProject()
        dao.insertProjects(listOf(cachedProject))
        val testSubscriber = dao.getProjects().test()
        testSubscriber.assertValue(listOf(cachedProject))
    }

    @Test
    fun deleteProjectsClearsData() {
        val cachedProject = CacheProjectDataFactory.makeCacheProject()
        dao.insertProjects(listOf(cachedProject))
        dao.deleteProjects()
        val testSubscriber = dao.getProjects().test()
        testSubscriber.assertValue(emptyList())
    }

    @Test
    fun getBookmarkedProjectReturnsData() {
        val cachedProject = CacheProjectDataFactory.makeBookmarkedCacheProject()
        dao.insertProjects(listOf(cachedProject))
        val testSubscriber = dao.getBookmarkedProjects().test()
        testSubscriber.assertValue(listOf(cachedProject))
    }

    @Test
    fun setBookmarkedProjectSavesData() {
        val cachedProject = CacheProjectDataFactory.makeCacheProject()
        val bookmarkedCachedProject = cachedProject.copy(isBookmarked = true)
        dao.insertProjects(listOf(cachedProject))
        dao.updateBookmarkStatus(true, cachedProject.id)
        val testSubscriber = dao.getBookmarkedProjects().test()
        testSubscriber.assertValue(listOf(bookmarkedCachedProject))
    }

    @Test
    fun setNotBookmarkedProjectSavesData() {
        val bookmarkedCachedProject = CacheProjectDataFactory.makeBookmarkedCacheProject()
        val cachedProject = bookmarkedCachedProject.copy(isBookmarked = false)
        dao.insertProjects(listOf(bookmarkedCachedProject))
        dao.updateBookmarkStatus(false, cachedProject.id)
        val testSubscriber = dao.getBookmarkedProjects().test()
        testSubscriber.assertValue(emptyList())
    }


    @After
    fun tearDown() {
        database.close()
    }

}