package com.sensehawk.cache.test.factory

import com.sensehawk.cache.model.Config

object ConfigDataFactory {

    fun makeCachedConfig(): Config {
        return Config(DataFactory.randomLong())
    }


}