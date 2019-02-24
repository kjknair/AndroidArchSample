package com.sensehawk.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sensehawk.cache.db.ConfigConstants
import com.sensehawk.cache.model.Config
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
abstract class ConfigDao {

    @Query(ConfigConstants.QUERY_CONFIG)
    abstract fun getConfig(): Flowable<Config>

    @Query(ConfigConstants.QUERY_CONFIG)
    abstract fun getConfigSingle(): Maybe<Config>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(config: Config)

}