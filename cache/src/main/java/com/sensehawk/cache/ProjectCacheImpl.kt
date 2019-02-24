package com.sensehawk.cache

import com.sensehawk.cache.db.ProjectsDatabase
import com.sensehawk.cache.mapper.CachedProjectMapper
import com.sensehawk.cache.model.Config
import com.sensehawk.data.model.ProjectEntity
import com.sensehawk.data.repository.ProjectsCache
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ProjectCacheImpl @Inject constructor(
    private val database: ProjectsDatabase,
    private val mapper: CachedProjectMapper
) : ProjectsCache {
    override fun clearCache(): Completable {
        return Completable.defer {
            database.cachedProjectsDao().deleteProjects()
            Completable.complete()
        }
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        return Completable.defer {
            database.cachedProjectsDao().insertProjects(projects.map { mapper.mapToCached(it) })
            Completable.complete()
        }
    }

    override fun getProjects(): Observable<List<ProjectEntity>> {
        return database.cachedProjectsDao().getProjects()
            .toObservable()
            .map { it.map { project -> mapper.mapFromCached(project) } }
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        return database.cachedProjectsDao().getBookmarkedProjects()
            .toObservable()
            .map { it.map { project -> mapper.mapFromCached(project) } }
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        return Completable.defer {
            database.cachedProjectsDao().updateBookmarkStatus(true, projectId)
            Completable.complete()
        }
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        return Completable.defer {
            database.cachedProjectsDao().updateBookmarkStatus(false, projectId)
            Completable.complete()
        }
    }

    override fun areProjectsCached(): Single<Boolean> {
        return database.cachedProjectsDao().getProjects().isEmpty
            .map { !it }
    }

    override fun setLastCacheTime(lastCacheTime: Long): Completable {
        return Completable.defer {
            database.configDao().insert(Config(lastCacheTime))
            Completable.complete()
        }
    }

    override fun isProjectsCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()
        val expirationTime = (1000 * 60 * 60 * 24).toLong()
        return database.configDao().getConfigSingle()
            .toSingle(Config(0.toLong()))
            .map { currentTime - it.lastCachedTime > expirationTime }
    }

}