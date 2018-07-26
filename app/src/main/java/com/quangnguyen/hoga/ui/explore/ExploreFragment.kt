package com.quangnguyen.hoga.ui.explore

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.quangnguyen.hoga.R
import com.quangnguyen.hoga.di.Injector
import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.ui.base.RecyclerViewListener
import com.quangnguyen.hoga.ui.imagedetail.ImageDetailActivity
import kotlinx.android.synthetic.main.fragment_images.*

class ExploreFragment : Fragment(), ExploreContract.View {

  private lateinit var presenter: ExploreContract.Presenter
  private lateinit var adapter: ImageAdapter

  private lateinit var searchView: SearchView

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    initPresenter()
    return inflater.inflate(R.layout.fragment_images, container, false)
  }

  override fun onStart() {
    super.onStart()
    setupViews()
  }

  private fun initPresenter() {
    presenter = ExplorePresenter(this, Injector.loadTrendingImagesUseCase,
        Injector.loadMoreTrendingImagesUseCase, Injector.searchImagesUseCase,
        Injector.searchMoreImagesUseCase, Injector.schedulerProvider)
  }

  private fun setupViews() {
    adapter = ImageAdapter(ArrayList())
    val layoutManager = GridLayoutManager(activity, 2)
    imageRecyclerView.layoutManager = layoutManager
    imageRecyclerView.adapter = adapter
    adapter.setOnItemClickListener(object : RecyclerViewListener.OnItemClickListener {
      override fun onItemClick(view: View, position: Int) {
        presenter.loadImageDetail(adapter.getItem(position).id)
      }
    })
    refreshLayout.setOnRefreshListener { presenter.loadTrendingImages() }

    // Setup paging
    val visibleThreshold = 1
    imageRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
        if (!refreshLayout.isRefreshing && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
          // Check if it is in 'Search' mode or not
          if (searchView.query.isNotEmpty()) {
            presenter.searchMoreImages(searchView.query.toString())
          } else {
            presenter.loadMoreTrendingImages()
          }
        }
      }
    })
  }

  override fun onResume() {
    super.onResume()
    presenter.attach()
  }

  override fun onPause() {
    super.onPause()
    presenter.detach()
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    if (inflater != null && menu != null) {
      inflater.inflate(R.menu.fragment_explore, menu)
      // Setup search widget in action bar
      searchView = menu.findItem(R.id.search).actionView as SearchView
      setupSearchView(searchView)
    }
  }

  private fun setupSearchView(searchView: SearchView) {
    searchView.queryHint = getString(R.string.search_image_hint)
    searchView.imeOptions = EditorInfo.IME_ACTION_DONE

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String): Boolean {
        searchView.clearFocus()

        presenter.searchImages(keyword = query)
        return true
      }

      override fun onQueryTextChange(newText: String): Boolean {
        return false
      }
    })

    searchView.setOnCloseListener {
      searchView.setQuery("", true)
      presenter.loadTrendingImages()
      false
    }
  }

  override fun showImages(images: List<Image>) {
    adapter.replaceData(images)
  }

  override fun showMoreImages(newImages: List<Image>) {
    adapter.addData(newImages)
  }

  override fun clearImages() {
    adapter.clearData()
  }

  override fun showErrorMessage(errorMsg: String) {
    Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show()
  }

  override fun startLoadingIndicator() {
    refreshLayout.post { refreshLayout.isRefreshing = true }
  }

  override fun stopLoadingIndicator() {
    refreshLayout.isRefreshing = false
  }

  override fun showImageDetail(imageId: String) {
    val intent = Intent(activity, ImageDetailActivity::class.java)
    intent.putExtra(ImageDetailActivity.EXTRA_IMAGE_ID, imageId)
    startActivity(intent)
  }
}