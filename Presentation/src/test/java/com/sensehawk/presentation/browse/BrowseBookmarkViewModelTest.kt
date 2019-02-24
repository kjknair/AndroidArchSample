package com.sensehawk.presentation.browse

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.sensehawk.domain.interactor.bookmarked.GetBookmarkedProjects
import com.sensehawk.domain.model.Project
import com.sensehawk.presentation.BrowseBookmarkViewModel
import com.sensehawk.presentation.mapper.ProjectViewMapper
import com.sensehawk.presentation.model.ProjectView
import com.sensehawk.presentation.state.ResourceState
import com.sensehawk.presentation.test.factory.DataFactory
import com.sensehawk.presentation.test.factory.ProjectFactory
import io.reactivex.observers.DisposableObserver
import org.junit.Rule
import org.junit.Test
import org.mockito.Captor
import kotlin.test.assertEquals


class BrowseBookmarkViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val getBookmarkedProjects = mock<GetBookmarkedProjects>()
    private val mapper = mock<ProjectViewMapper>()
    private val viewModel = BrowseBookmarkViewModel(
        getBookmarkedProjects, mapper
    )

    @Captor
    val captor = argumentCaptor<DisposableObserver<List<Project>>>()

    @Test
    fun getBookmarkedProjectsExecutesUseCase() {
        viewModel.getBookmarkedProjects()
        verify(getBookmarkedProjects).execute(any(), eq(null))
    }

    @Test
    fun getBookmarkedProjectsReturnsSuccess() {
        val projectList = ProjectFactory.makeProjectList()
        val projectViewList = ProjectFactory.makeProjectViewList()

        val zip = projectList.zip(projectViewList)
        zip.forEach { stubMapperMapToView(it.first, it.second) }

        viewModel.getBookmarkedProjects()
        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projectList)
        assertEquals(
            viewModel.liveData.value?.state,
            ResourceState.SUCCESS
        )
    }

    @Test
    fun getBookmarkedProjectsReturnsData() {
        val projectList = ProjectFactory.makeProjectList()
        val projectViewList = ProjectFactory.makeProjectViewList()

        val zip = projectList.zip(projectViewList)
        zip.forEach { stubMapperMapToView(it.first, it.second) }

        viewModel.getBookmarkedProjects()
        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projectList)
        assertEquals(viewModel.liveData.value?.data, projectViewList)
    }

    @Test
    fun getBookmarkedProjectsReturnsError() {
        viewModel.getBookmarkedProjects()
        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(DataFactory.randomString()))
        assertEquals(viewModel.liveData.value?.state, ResourceState.ERROR)
    }

    @Test
    fun getBookmarkedProjectsReturnsErrorMessage() {
        val errorMessage = DataFactory.randomString()
        viewModel.getBookmarkedProjects()
        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(errorMessage))
        assertEquals(viewModel.liveData.value?.message, errorMessage)
    }

    private fun stubMapperMapToView(project: Project, view: ProjectView) {
        whenever(mapper.mapToView(project))
            .thenReturn(view)
    }


}