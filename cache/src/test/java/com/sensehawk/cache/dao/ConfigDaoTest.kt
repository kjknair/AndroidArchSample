package com.sensehawk.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.sensehawk.cache.db.ProjectsDatabase
import com.sensehawk.cache.model.Config
import com.sensehawk.cache.test.factory.DataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ConfigDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    val database: ProjectsDatabase = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application.applicationContext,
        ProjectsDatabase::class.java
    ).allowMainThreadQueries()
        .build()
    val dao
        get() = database.configDao()

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun getConfigReturnsData() {
        val data = Config(DataFactory.randomLong())
        dao.insert(data)
        val testSubscriber = dao.getConfig().test()
        testSubscriber.assertValue {
            it.lastCachedTime == data.lastCachedTime && it.id != 0L
        }
    }


}