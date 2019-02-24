package com.sensehawk.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sensehawk.domain.interactor.bookmarked.GetBookmarkedProjects
import com.sensehawk.domain.model.Project
import com.sensehawk.presentation.mapper.ProjectViewMapper
import com.sensehawk.presentation.model.ProjectView
import com.sensehawk.presentation.state.Resource
import com.sensehawk.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseBookmarkViewModel @Inject constructor(
    private val getBookmarkedProjects: GetBookmarkedProjects,
    private val mapper: ProjectViewMapper
):ViewModel() {

    private val mutableLiveData = MutableLiveData<Resource<List<ProjectView>>>()
    val liveData: LiveData<Resource<List<ProjectView>>>
        get() = mutableLiveData

    fun getBookmarkedProjects() {
        mutableLiveData.postValue(Resource(ResourceState.LOADING, liveData.value?.data))
        getBookmarkedProjects.execute(GetBookmarkedProjectSubscriber())
    }

    override fun onCleared() {
        getBookmarkedProjects.dispose()
        super.onCleared()
    }

    inner class GetBookmarkedProjectSubscriber : DisposableObserver<List<Project>>() {
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