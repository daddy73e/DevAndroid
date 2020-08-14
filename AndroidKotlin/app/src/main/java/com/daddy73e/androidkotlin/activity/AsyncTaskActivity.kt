package com.daddy73e.androidkotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.daddy73e.androidkotlin.R
import com.daddy73e.androidkotlin.databinding.ActivityAsyncTaskBinding

class AsyncTaskActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAsyncTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_async_task
        )
    }
}