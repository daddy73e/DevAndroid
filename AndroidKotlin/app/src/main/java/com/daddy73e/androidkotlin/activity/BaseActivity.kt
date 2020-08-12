package com.daddy73e.androidkotlin.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.daddy73e.androidkotlin.views.LoadingView
import kotlinx.android.synthetic.main.layout_loading.view.*

open class BaseActivity: AppCompatActivity()  {

    var loadingView: LoadingView? = null

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        setUpLoadingView(window.decorView.findViewById(android.R.id.content))
    }

    private fun setUpLoadingView(rootLayout: ViewGroup) {
        if(loadingView != null) {
            return
        }

        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT)
        loadingView = LoadingView(this)
        loadingView?.layoutParams = layoutParams
        loadingView?.visibility = View.GONE

        rootLayout.addView(loadingView)
    }

    fun showLoading(isCancelable: Boolean) {
        loadingView?.visibility = View.VISIBLE
        if(isCancelable) {
            loadingView?.btnStop?.visibility = View.VISIBLE
        } else {
            loadingView?.btnStop?.visibility = View.GONE
        }
    }

    fun hideLoading() {
        loadingView?.visibility = View.GONE
    }
}