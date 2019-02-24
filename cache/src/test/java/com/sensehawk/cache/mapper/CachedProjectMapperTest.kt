package com.sensehawk.cache.mapper

import com.sensehawk.cache.model.CachedProject
import com.sensehawk.cache.test.factory.CacheProjectDataFactory
import com.sensehawk.data.model.ProjectEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class CachedProjectMapperTest {

    private val mapper = CachedProjectMapper()

    @Test
    fun mapToCachedMapsData() {
        val entity = CacheProjectDataFactory.makeProjectEntity()
        val cached = mapper.mapToCached(entity)
        assertEqualsData(entity, cached)
    }

    @Test
    fun mapFromCachedMapsData() {
        val cached = CacheProjectDataFactory.makeCacheProject()
        val entity = mapper.mapFromCached(cached)
        assertEqualsData(entity, cached)
    }

    private fun assertEqualsData(entity: ProjectEntity, cached: CachedProject) {
        assertEquals(entity.id, cached.id)
        assertEquals(entity.name, cached.name)
        assertEquals(entity.fullName, cached.fullName)
        assertEquals(entity.starCount, cached.starCount)
        assertEquals(entity.dateCreated, cached.dateCreated)
        assertEquals(entity.ownerName, cached.ownerName)
        assertEquals(entity.ownerAvatar, cached.ownerAvatar)
        assertEquals(entity.isBookmarked, cached.isBookmarked)
    }


}