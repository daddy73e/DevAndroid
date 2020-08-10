package com.daddy73e.androidkotlin.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RelativeLayout
import com.daddy73e.androidkotlin.R


class LoadingView @JvmOverloads constructor(context: Context,
                                                  attrs: AttributeSet? = null,
                                                  defStyleAttr: Int = 0):
    RelativeLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.layout_loading, this, true)
    }
}