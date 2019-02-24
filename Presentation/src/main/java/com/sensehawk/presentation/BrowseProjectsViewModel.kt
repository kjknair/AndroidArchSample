package com.sensehawk.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sensehawk.domain.interactor.bookmarked.BookmarkProject
import com.sensehawk.domain.interactor.bookmarked.UnbookmarkProject
import com.sensehawk.domain.interactor.browse.GetProjects
import com.sensehawk.domain.model.Project
import com.sensehawk.presentation.mapper.ProjectViewMapper
import com.sensehawk.presentation.model.ProjectView
import com.sensehawk.presentation.state.Resource
import com.sensehawk.presentation.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseProjectsViewModel @Inject constructor(
    private val getProjects: GetProjects,
    private val bookmarkProject: BookmarkProject,
    private val unbookmarkProject: UnbookmarkProject,
    private val mapper: ProjectViewMapper
) : ViewModel() {

    private val mutableLiveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    val liveData: LiveData<Resource<List<ProjectView>>>
        get() = mutableLiveData

    fun fetchProjects() {
        mutableLiveData.postValue(Resource(ResourceState.LOADING))
        getProjects.execute(ProjectsSubscriber())
    }

    fun unBookmarkProject(projectId: String) {
        unbookmarkProject.execute(BookmarkSubscriber(), UnbookmarkProject.Params.forProject(projectId))
    }

    fun bookmarkProject(projectId: String) {
        bookmarkProject.execute(BookmarkSubscriber(), BookmarkProject.Params.forProject(projectId))
    }

    override fun onCleared() {
        getProjects.dispose()
        super.onCleared()
    }

    inner class BookmarkSubscriber : DisposableCompletableObserver() {
        override fun onComplete() {
            mutableLiveData.postValue(Resource(ResourceState.SUCCESS, mutableLiveData.value?.data))
        }

        override fun onError(e: Throwable) {
            mutableLiveData.postValue(Resource(ResourceState.ERROR))
        }

    }

    inner class ProjectsSubscriber : DisposableObserver<List<Project>>() {
        override fun onComplete() {

        }

        override fun onNext(value: List<Project>) {
            mutableLiveData.postValue(Resource(ResourceState.SUCCESS, value.map { mapper.mapToView(it) }))
        }

        override fun onError(error: Throwable) {
            mutableLiveData.postValue(Resource(ResourceState.ERROR, message = error.localizedMessage))
        }

    }

}
