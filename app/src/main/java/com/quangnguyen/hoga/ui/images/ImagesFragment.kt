package com.quangnguyen.hoga.ui.images

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.quangnguyen.hoga.R
import com.quangnguyen.hoga.di.Injector
import com.quangnguyen.hoga.domain.model.Image
import kotlinx.android.synthetic.main.fragment_images.*

class ImagesFragment : Fragment(), ImagesContract.View {

  lateinit var presenter: ImagesPresenter
  lateinit var adapter: ImageAdapter

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val rootView = inflater.inflate(R.layout.fragment_images, container, false)
    return rootView
  }

  override fun onStart() {
    super.onStart()
    initPresenter()
    setupViews()
  }

  private fun initPresenter() {
    presenter = ImagesPresenter(this, Injector.loadTrendingImageUseCase, Injector.schedulerProvider)
  }

  private fun setupViews() {
    adapter = ImageAdapter(emptyList())
    val layoutManager = GridLayoutManager(activity, 2)
    imageRecyclerView.layoutManager = layoutManager
    imageRecyclerView.adapter = adapter
  }

  override fun onResume() {
    super.onResume()
    presenter.attach()
  }

  override fun onPause() {
    super.onPause()
    presenter.detach()
  }

  override fun showTrendingImages(images: List<Image>) {
    adapter.replaceData(images)
  }

  override fun showErrorMessage(errorMsg: String) {
    Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show()
  }
}