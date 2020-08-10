package com.daddy73e.androidkotlin.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.daddy73e.androidkotlin.R
import kotlinx.android.synthetic.main.layout_loading.view.*
import com.daddy73e.androidkotlin.databinding.ActivityCoroutineBinding
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CoroutineActivity : BaseActivity(), CoroutineScope {

    private lateinit var binding: ActivityCoroutineBinding
    lateinit var defaultJob: Job
    private var isCounting = false
    private var flagLoadingView = true /* 로딩 화면을 보여주는 flag */

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + defaultJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        defaultJob = Job()
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_coroutine
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        defaultJob.cancel()
    }

    override fun onStart() {
        super.onStart()
        loadingView?.btnStop?.setOnClickListener {
            onStopTimer()
        }
    }

    fun onClickStart(view: View) {
        onStartTimer()
    }

    fun onClickStop(view: View) {
        onStopTimer()
    }

    fun onClickQuiz(view: View) {
        onStartQuiz()
    }

    fun onClickRetry(view: View) {
        onResetQuiz()
    }

    private fun onStartTimer() {
        if(flagLoadingView) {
            showLoading(true)
        }

        isCounting = true
        defaultJob = GlobalScope.launch(Dispatchers.Default) {
            for (i in 10 downTo 0) {
                if(i == 0 || !isCounting) {
                    defaultJob.cancel()
                    onStopTimer()
                } else {
                    GlobalScope.launch(Dispatchers.Main){
                        if(isCounting) {
                            txtNumber.text = "$i"
                        }
                    }
                    delay(1000)
                }
            }
        }
    }

    private fun onStopTimer() {
        isCounting = false
        GlobalScope.launch(Dispatchers.Main){
            txtNumber.text = getString(R.string.timer)
            if(flagLoadingView) {
                hideLoading()
            }
        }
    }

    private fun onStartQuiz() {
        btnQuizStart.visibility = View.GONE
        layoutQuiz.visibility = View.VISIBLE
        txtAnswer.visibility = View.GONE
        btnRetry.visibility = View.GONE
        txtQuizTimer.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.Default) {
            val deferred : Deferred<String> = async {
                var i = 0
                while (i < 10) {
                    delay(1000)
                    i++
                    val time = 10 - i
                    GlobalScope.launch(Dispatchers.Main) {
                        txtQuizTimer.text = "$time"
                    }
                    println(i)
                }

                getString(R.string.txt_answer)
            }

            val answer = deferred.await() /* deferred 의 결과값 */

            GlobalScope.launch(Dispatchers.Main){
                txtAnswer.text = answer
                txtQuizTimer.visibility = View.GONE
                btnQuizStart.visibility = View.GONE
                layoutQuiz.visibility = View.VISIBLE
                txtAnswer.visibility = View.VISIBLE
                btnRetry.visibility = View.VISIBLE
            }
        }
    }

    private fun onResetQuiz() {
        btnQuizStart.visibility = View.VISIBLE
        layoutQuiz.visibility = View.GONE
    }
}