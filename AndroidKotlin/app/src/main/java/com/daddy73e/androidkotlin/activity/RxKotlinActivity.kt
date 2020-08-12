package com.daddy73e.androidkotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.daddy73e.androidkotlin.R
import com.daddy73e.androidkotlin.databinding.ActivityKotlinBinding
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.subjects.BehaviorSubject


class RxKotlinActivity: AppCompatActivity(){

    private lateinit var binding: ActivityKotlinBinding
    private var viewModel = RxKotlinViewModel()

    private var compositeDispose = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_kotlin
        )

        val disposeCount = viewModel.getValueStream()
            .map { it.toString() }
            .subscribe { binding.txtNum.text = it }

        val disposeMerge = Observable
            .merge(
            binding.btnMinus.clicks().map{-1} ,
            binding.btnPlus.clicks().map{1} )
            .withLatestFrom(viewModel.getValueStream(), BiFunction { t1 : Int, t2 : Int ->  t1 + t2})
            .subscribe {viewModel.setCount(it)}

        compositeDispose.addAll(disposeCount, disposeMerge)
    }

    override fun onDestroy() {
        compositeDispose.dispose()
        super.onDestroy()
    }

}