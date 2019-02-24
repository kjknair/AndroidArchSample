package com.sensehawk.cache.mapper

import com.sensehawk.cache.model.CachedProject
import com.sensehawk.data.model.ProjectEntity
import javax.inject.Inject

class CachedProjectMapper @Inject constructor() : CacheMapper<CachedProject, ProjectEntity> {
    override fun mapToCached(entity: ProjectEntity): CachedProject {
        return with(entity) {
            CachedProject(
                id,
                name,
                fullName,
                starCount,
                dateCreated,
                ownerName,
                ownerAvatar,
                isBookmarked
            )
        }
    }

    override fun mapFromCached(cache: CachedProject): ProjectEntity {
        return with(cache) {
            ProjectEntity(
                id,
                name,
                fullName,
                starCount,
                dateCreated,
                ownerName,
                ownerAvatar,
                isBookmarked
            )
        }
    }
}