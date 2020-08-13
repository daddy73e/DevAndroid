package com.daddy73e.androidkotlin.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.daddy73e.androidkotlin.R
import com.daddy73e.androidkotlin.databinding.ActivityKotlinBinding
import com.daddy73e.androidkotlin.mvvm.MVVMActivity
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.android.synthetic.main.activity_kotlin.*


class RxKotlinActivity: AppCompatActivity(){

    private lateinit var binding: ActivityKotlinBinding
    private var viewModel = RxKotlinViewModel()
    private var compositeDispose = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_kotlin
        )

        viewModel.getValueStream()
            .map { it.toString() }
            .subscribe { txtNum.text = it }
            .addTo(compositeDispose)

        Observable
            .merge(btnMinus.clicks().map{-1} ,
            btnPlus.clicks().map{1} )
            .withLatestFrom(viewModel.getValueStream(), BiFunction<Int, Int, Int> { n, c ->  n + c })
            .subscribe {viewModel.setValue(it)}
            .addTo(compositeDispose)

        btnMvvm.clicks().subscribe{
            goMvvmActivity()
        }.addTo(compositeDispose)
    }

    fun goMvvmActivity() {
        viewModel.getValueStream()
            .take(1)
            .map { value ->
                val intent = Intent(this, MVVMActivity::class.java)
                intent.putExtra("value", value)
                intent
            }
            .subscribe {
                intent -> startActivityForResult(intent, 1000)
            }
            .addTo(compositeDispose)
    }

    override fun onDestroy() {
        compositeDispose.dispose()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1000) {
            if(resultCode == Activity.RESULT_OK) {
                val value = data?.getIntExtra("value", 0) ?: 0
                viewModel.setValue(value)
            }
        }
    }
}