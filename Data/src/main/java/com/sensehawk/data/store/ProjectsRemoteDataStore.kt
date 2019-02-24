package com.sensehawk.data.store

import com.sensehawk.data.model.ProjectEntity
import com.sensehawk.data.repository.ProjectsDataStore
import com.sensehawk.data.repository.ProjectsRemote
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

open class ProjectsRemoteDataStore @Inject constructor(
    private val projectsRemote: ProjectsRemote
) : ProjectsDataStore {
    override fun getProjects(): Observable<List<ProjectEntity>> {
        return projectsRemote.getProjects()
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun clearProjects(): Completable {
        throw UnsupportedOperationException()
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        throw UnsupportedOperationException()
    }

    override fun setProjectAsBookmark(projectId: String): Completable {
        throw UnsupportedOperationException()
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException()
    }
}