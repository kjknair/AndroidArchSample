package com.sensehawk.cache.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.sensehawk.cache.db.ProjectConstants
import com.sensehawk.cache.db.ProjectConstants.COLUMN_IS_BOOKMARKED
import com.sensehawk.cache.db.ProjectConstants.COLUMN_PROJECT_ID

@Entity(tableName = ProjectConstants.TABLE_NAME)
data class CachedProject(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_PROJECT_ID)
    val id: String,
    val name: String,
    val fullName: String,
    val starCount: String,
    val dateCreated: String,
    val ownerName: String,
    val ownerAvatar: String,
    @ColumnInfo(name = COLUMN_IS_BOOKMARKED)
    val isBookmarked: Boolean
)