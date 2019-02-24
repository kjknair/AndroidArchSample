package com.sensehawk.mobile_ui.browse

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.whenever
import com.sensehawk.domain.model.Project
import com.sensehawk.mobile_ui.R
import com.sensehawk.mobile_ui.test.TestApplication
import com.sensehawk.mobile_ui.test.factory.ProjectDataFactory
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_browse.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BrowseActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule<BrowseActivity>(BrowseActivity::class.java, false, false)

    @Test
    fun activityLaunches() {
        stubProjectRepositoryGetProjects(Observable.just(listOf(ProjectDataFactory.makeProject())))
        activityTestRule.launchActivity(null)
    }

    @Test
    fun projectDisplay() {
        val projects = mutableListOf<Project>()
        repeat(times = 10, action = {
            projects.add(ProjectDataFactory.makeProject())
        })
        stubProjectRepositoryGetProjects(Observable.just(projects))
        activityTestRule.launchActivity(null)
        projects.forEachIndexed { index, project ->
            onView(withId(R.id.recycler_projects))
                    .perform(RecyclerViewActions.scrollToPosition<BrowseAdapter.ViewHolder>(index))
            onView(withId(R.id.recycler_projects))
                .check(matches(hasDescendant(withText(project.fullName))))
        }
    }


    private fun stubProjectRepositoryGetProjects(observable: Observable<List<Project>>) {
        whenever(TestApplication.appComponent().projectRepository().getProjects())
            .thenReturn(observable)
    }


}