package com.daddy73e.androidkotlin.mvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.daddy73e.androidkotlin.R
import com.daddy73e.androidkotlin.activity.BaseActivity
import com.daddy73e.androidkotlin.databinding.ActivityMvvmBinding
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.android.synthetic.main.activity_mvvm.*

class MVVMActivity: BaseActivity() {

    private lateinit var viewModel: MVVMViewModel
    private lateinit var binding:ActivityMvvmBinding
    private val disposeBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        viewModel = MVVMViewModel()

        val count:Int = intent.getIntExtra("value", 0)
        binding.editNumber.setText("$count", TextView.BufferType.EDITABLE)

        btnInput.clicks()
            .subscribe {
                val intent = Intent()
                intent.putExtra("value", editNumber.text.toString().toIntOrNull() ?:0)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            .addTo(disposeBag)
    }

    override fun onDestroy() {
        disposeBag.dispose()
        super.onDestroy()
    }
}

