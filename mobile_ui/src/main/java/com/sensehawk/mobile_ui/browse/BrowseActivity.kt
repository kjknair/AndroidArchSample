package com.sensehawk.mobile_ui.browse

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.sensehawk.mobile_ui.R
import com.sensehawk.mobile_ui.bookmarked.BookmarkedActivity
import com.sensehawk.mobile_ui.injection.ViewModelFactory
import com.sensehawk.mobile_ui.mapper.ProjectViewMapper
import com.sensehawk.presentation.BrowseProjectsViewModel
import com.sensehawk.presentation.model.ProjectView
import com.sensehawk.presentation.state.Resource
import com.sensehawk.presentation.state.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_browse.*
import timber.log.Timber
import javax.inject.Inject

class BrowseActivity : AppCompatActivity() {

    lateinit var browseViewModel: BrowseProjectsViewModel
    @Inject
    lateinit var mapper: ProjectViewMapper
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var adapter: BrowseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        AndroidInjection.inject(this)
        setupBrowseRecyclerView()
        browseViewModel = ViewModelProviders.of(this, viewModelFactory).get(BrowseProjectsViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        browseViewModel.liveData.observe(this, Observer {
            it?.let(::handleProjectsResponse)
        })
        browseViewModel.fetchProjects()
    }

    private fun handleProjectsResponse(resource: Resource<List<ProjectView>>) {
        Timber.d("#handleProjectsResponse $resource")
        when (resource.state) {
            ResourceState.LOADING -> {
                progress.visibility = View.VISIBLE
                recycler_projects.visibility = View.INVISIBLE
                errorText.visibility = View.INVISIBLE
            }
            ResourceState.SUCCESS -> {
                progress.visibility = View.INVISIBLE
                recycler_projects.visibility = View.VISIBLE
                errorText.visibility = View.INVISIBLE
                adapter.projects = resource.data!!.map { mapper.mapToView(it) }
                adapter.notifyDataSetChanged()
            }
            ResourceState.ERROR -> {
                progress.visibility = View.INVISIBLE
                recycler_projects.visibility = View.INVISIBLE
                errorText.visibility = View.VISIBLE
                errorText.text = resource.message
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_bookmarked -> {
                startActivity(BookmarkedActivity.getStartIntent(this))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setupBrowseRecyclerView() {
        recycler_projects.layoutManager = LinearLayoutManager(this)
        adapter.projectListener = object: ProjectListener{
            override fun onBookmarkedProjectClicked(projectId: String) {
                browseViewModel.unBookmarkProject(projectId)
            }

            override fun onProjectClicked(projectId: String) {
                browseViewModel.bookmarkProject(projectId)
            }

        }
        recycler_projects.adapter = adapter
    }
}
