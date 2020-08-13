package com.daddy73e.androidkotlin.activity

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class RxKotlinViewModel {

    private var count: BehaviorSubject<Int> = BehaviorSubject.createDefault(0)

    fun getValue():Int {
        return count.value
    }

    fun getValueStream():Observable<Int> {
        return count
    }

    fun setValue(n:Int) {
        count.onNext(n)
    }
}