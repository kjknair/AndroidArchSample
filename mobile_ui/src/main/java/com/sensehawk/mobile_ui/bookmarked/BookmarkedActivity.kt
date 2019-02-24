package com.sensehawk.mobile_ui.bookmarked

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.sensehawk.mobile_ui.R
import com.sensehawk.mobile_ui.injection.ViewModelFactory
import com.sensehawk.mobile_ui.mapper.ProjectViewMapper
import com.sensehawk.presentation.BrowseBookmarkViewModel
import com.sensehawk.presentation.model.ProjectView
import com.sensehawk.presentation.state.Resource
import com.sensehawk.presentation.state.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_bookmarked.*
import timber.log.Timber
import javax.inject.Inject

class BookmarkedActivity : AppCompatActivity() {

    @Inject
    lateinit var mapper: ProjectViewMapper
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var adapter: BookmarkedAdapter
    lateinit var browseViewModel: BrowseBookmarkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarked)
        AndroidInjection.inject(this)
        browseViewModel = ViewModelProviders.of(this, viewModelFactory).get(BrowseBookmarkViewModel::class.java)
        setUpRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        browseViewModel.liveData.observe(this, Observer {
            it?.let(::handleDataState)
        })
        browseViewModel.getBookmarkedProjects()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpRecyclerView() {
        recycler_projects.layoutManager = LinearLayoutManager(this)
        recycler_projects.adapter = adapter
    }

    private fun handleDataState(resource: Resource<List<ProjectView>>) {
        Timber.e("resource $resource")
        when (resource.state) {
            ResourceState.LOADING -> {
                progress.visibility = View.VISIBLE
                recycler_projects.visibility = View.INVISIBLE
            }
            ResourceState.SUCCESS -> {
                progress.visibility = View.INVISIBLE
                recycler_projects.visibility = View.VISIBLE
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

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, BookmarkedActivity::class.java)
        }
    }


}
