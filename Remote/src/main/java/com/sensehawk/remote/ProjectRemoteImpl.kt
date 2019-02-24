package com.sensehawk.remote

import com.sensehawk.data.model.ProjectEntity
import com.sensehawk.data.repository.ProjectsRemote
import com.sensehawk.remote.mapper.ProjectResponseModelMapper
import com.sensehawk.remote.service.GithubTrendingService
import io.reactivex.Observable
import javax.inject.Inject

class ProjectRemoteImpl @Inject constructor(
    private val service: GithubTrendingService,
    private val mapper: ProjectResponseModelMapper
) : ProjectsRemote {
    override fun getProjects(): Observable<List<ProjectEntity>> {
        return service.searchRepositories("language:kotlin", "stars", "desc")
            .map { it.items.map { projectModel -> mapper.mapFromModel(projectModel) } }
    }
}
