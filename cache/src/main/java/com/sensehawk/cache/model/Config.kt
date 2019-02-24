package com.sensehawk.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.sensehawk.cache.db.ConfigConstants

@Entity(tableName = ConfigConstants.TABLE_NAME)
data class Config(
    val lastCachedTime: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)