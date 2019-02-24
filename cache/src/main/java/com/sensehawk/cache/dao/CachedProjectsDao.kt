package com.sensehawk.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sensehawk.cache.db.ProjectConstants.COLUMN_IS_BOOKMARKED
import com.sensehawk.cache.db.ProjectConstants.QUERY_BOOKMARKED_PROJECT
import com.sensehawk.cache.db.ProjectConstants.QUERY_DELETE_PROJECT
import com.sensehawk.cache.db.ProjectConstants.QUERY_PROJECTS
import com.sensehawk.cache.db.ProjectConstants.TABLE_NAME
import com.sensehawk.cache.model.CachedProject
import io.reactivex.Flowable

@Dao
abstract class CachedProjectsDao {

    @Query(QUERY_PROJECTS)
    abstract fun getProjects(): Flowable<List<CachedProject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProjects(projects: List<CachedProject>)

    @Query(QUERY_BOOKMARKED_PROJECT)
    abstract fun getBookmarkedProjects(): Flowable<List<CachedProject>>

    @Query(QUERY_DELETE_PROJECT)
    abstract fun deleteProjects()

    @Query("UPDATE $TABLE_NAME SET $COLUMN_IS_BOOKMARKED=:isBookmarked where projectId=:projectId")
    abstract fun updateBookmarkStatus(isBookmarked: Boolean, projectId: String): Int

}