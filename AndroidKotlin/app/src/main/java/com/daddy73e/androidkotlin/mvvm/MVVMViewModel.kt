package com.daddy73e.androidkotlin.mvvm

import android.view.View
import androidx.lifecycle.ViewModel


class MVVMViewModel() : ViewModel() {

    var title:String = ""

    fun onClickButton(view: View) {
        println("CLICK ${view.layoutParams.width}")
    }

}
