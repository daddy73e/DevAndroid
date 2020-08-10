package com.daddy73e.androidkotlin.mvvm

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.daddy73e.androidkotlin.R
import com.daddy73e.androidkotlin.activity.BaseActivity
import com.daddy73e.androidkotlin.databinding.ActivityMvvmBinding

class MVVMActivity: BaseActivity() {

    private lateinit var viewModel: MVVMViewModel
    private lateinit var binding:ActivityMvvmBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        viewModel = MVVMViewModel()

    }
}

