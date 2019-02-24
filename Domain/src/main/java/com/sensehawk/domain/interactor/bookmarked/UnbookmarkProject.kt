package com.sensehawk.domain.interactor.bookmarked

import com.sensehawk.domain.executor.PostExecutionThread
import com.sensehawk.domain.interactor.CompletableUseCase
import com.sensehawk.domain.repository.ProjectRepository
import io.reactivex.Completable
import javax.inject.Inject

open class UnbookmarkProject @Inject constructor(
    val projectRepository: ProjectRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<UnbookmarkProject.Params>(postExecutionThread) {

    public override fun buildUseCaseCompletable(params: Params?): Completable {
        checkNotNull(params)
        return projectRepository.unbookmarkProject(params!!.projectId)
    }

    data class Params(val projectId: String) {
        companion object {
            fun forProject(projectId: String): Params {
                return Params(projectId)
            }
        }
    }
}
