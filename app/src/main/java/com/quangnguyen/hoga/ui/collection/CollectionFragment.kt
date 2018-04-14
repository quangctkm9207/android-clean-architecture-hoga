package com.quangnguyen.hoga.ui.collection

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.quangnguyen.hoga.R
import com.quangnguyen.hoga.di.Injector
import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.ui.base.RecyclerViewListener
import com.quangnguyen.hoga.ui.imagedetail.ImageDetailActivity
import com.quangnguyen.hoga.ui.explore.ImageAdapter
import kotlinx.android.synthetic.main.fragment_images.imageRecyclerView

class CollectionFragment : Fragment(), CollectionContract.View {

  lateinit var presenter: CollectionContract.Presenter
  lateinit var adapter: ImageAdapter

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    initPresenter()
    return inflater.inflate(R.layout.fragment_collection, container, false)
  }

  override fun onStart() {
    super.onStart()
    setupViews()
  }

  private fun initPresenter() {
    presenter = CollectionPresenter(this,
        Injector.loadDownloadedImagesUseCase, Injector.schedulerProvider)
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
  }

  override fun onResume() {
    super.onResume()
    presenter.attach()
  }

  override fun onPause() {
    super.onPause()
    presenter.detach()
  }

  override fun showDownloadedImages(images: List<Image>) {
    adapter.replaceData(images)
  }

  override fun showErrorMessage(errorMsg: String) {
    Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show()
  }

  override fun showImageDetail(imageId: String) {
    val intent = Intent(activity, ImageDetailActivity::class.java)
    intent.putExtra(ImageDetailActivity.EXTRA_IMAGE_ID, imageId)
    startActivity(intent)
  }
}