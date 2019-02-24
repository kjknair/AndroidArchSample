package com.sensehawk.domain.interactor.browse

import com.sensehawk.domain.executor.PostExecutionThread
import com.sensehawk.domain.interactor.ObservableUseCase
import com.sensehawk.domain.model.Project
import com.sensehawk.domain.repository.ProjectRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetProjects @Inject constructor(
    private val projectRepository: ProjectRepository,
    postExecutionThread: PostExecutionThread
): ObservableUseCase<List<Project>, Nothing?>(postExecutionThread){

    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<Project>> {
        return projectRepository.getProjects()
    }

}
