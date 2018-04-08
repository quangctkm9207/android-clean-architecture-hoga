package com.quangnguyen.hoga.ui.images

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
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
import com.quangnguyen.hoga.util.hideKeyboard
import kotlinx.android.synthetic.main.fragment_images.*

class ImagesFragment : Fragment(), ImagesContract.View {

  private lateinit var presenter: ImagesContract.Presenter
  private lateinit var adapter: ImageAdapter

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_images, container, false)
  }

  override fun onStart() {
    super.onStart()
    setupViews()
  }

  fun setPresenter(presenter: ImagesContract.Presenter) {
    this.presenter = presenter
  }

  private fun setupViews() {
    adapter = ImageAdapter(emptyList())
    val layoutManager = GridLayoutManager(activity, 2)
    imageRecyclerView.layoutManager = layoutManager
    imageRecyclerView.adapter = adapter
    adapter.setOnItemClickListener(object : RecyclerViewListener.OnItemClickListener {
      override fun onItemClick(view: View, position: Int) {
        presenter.loadImageDetail(adapter.getItem(position).id)
      }
    })
    refreshLayout.setOnRefreshListener { presenter.loadTrendingImages() }
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
      inflater.inflate(R.menu.fragment_images, menu)
      // Setup search widget in action bar
      val searchView = menu.findItem(R.id.search).actionView as SearchView
      setupSearchView(searchView)
    }
  }

  private fun setupSearchView(searchView: SearchView) {
    searchView.queryHint = getString(R.string.search_image_hint)
    searchView.imeOptions = EditorInfo.IME_ACTION_DONE

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String): Boolean {
        activity?.let { hideKeyboard(it) }

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