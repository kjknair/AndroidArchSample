package com.sensehawk.data

import com.sensehawk.data.mapper.ProjectMapper
import com.sensehawk.data.repository.ProjectsCache
import com.sensehawk.data.store.ProjectsDataStoreFactory
import com.sensehawk.domain.model.Project
import com.sensehawk.domain.repository.ProjectRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import javax.inject.Inject

class ProjectsDataRepository @Inject constructor(
    private val mapper: ProjectMapper,
    private val factory: ProjectsDataStoreFactory,
    private val cache: ProjectsCache
) : ProjectRepository {
    override fun getProjects(): Observable<List<Project>> {
        return Observables.zip(
            cache.areProjectsCached().toObservable(),
            cache.isProjectsCacheExpired().toObservable()
        ) { areCached, isExpired -> Pair(areCached, isExpired) }
            .flatMap { factory.getDataStore(it.first, it.second).getProjects() }
            .flatMap { projects ->
                factory.getCacheDataStore()
                    .saveProjects(projects)
                    .andThen(Observable.just(projects))
            }
            .map { projects -> projects.map { mapper.mapFromEntity(it) } }
    }

    override fun bookmarkProject(projectId: String): Completable {
        return factory.getCacheDataStore().setProjectAsBookmark(projectId)
    }

    override fun unbookmarkProject(projectId: String): Completable {
        return factory.getCacheDataStore().setProjectAsNotBookmarked(projectId)
    }

    override fun getBookmarkedProjects(): Observable<List<Project>> {
        return factory.getCacheDataStore().getBookmarkedProjects()
            .map { projects -> projects.map { mapper.mapFromEntity(it) } }
    }
}