package com.sensehawk.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.sensehawk.cache.dao.CachedProjectsDao
import com.sensehawk.cache.dao.ConfigDao
import com.sensehawk.cache.model.CachedProject
import com.sensehawk.cache.model.Config

@Database(entities = [CachedProject::class, Config::class], version = 1)
abstract class ProjectsDatabase : RoomDatabase() {

    abstract fun cachedProjectsDao(): CachedProjectsDao

    abstract fun configDao(): ConfigDao

    companion object {

        private var INSTANCE: ProjectsDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): ProjectsDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ProjectsDatabase::class.java, "projects.db"
                        ).build()
                    }
                    return INSTANCE as ProjectsDatabase
                }
            }
            return INSTANCE as ProjectsDatabase
        }
    }

}