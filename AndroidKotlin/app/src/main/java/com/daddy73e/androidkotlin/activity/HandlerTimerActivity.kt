package com.daddy73e.androidkotlin.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.daddy73e.androidkotlin.R
import com.daddy73e.androidkotlin.databinding.ActivityHandlerTimerBinding

enum class TimeState(val value: Int) {
    NONE(0),
    PLAYING(1),
    STOP(2),
    FINISH(-1)
}

class HandlerTimerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHandlerTimerBinding
    private lateinit var thread: TimerThread

    private var timeState: TimeState = TimeState.NONE
    private var currentTime: Int = 0
    private val finishTime: Int = 10

    private val handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                TimeState.NONE.value -> {
                    binding.txtView.text = msg.arg1.toString() + msg.obj
                    timeState = TimeState.NONE
                }

                TimeState.PLAYING.value -> {
                    currentTime = msg.arg1
                    binding.txtView.text = msg.arg1.toString() + msg.obj
                    timeState = TimeState.PLAYING
                }

                TimeState.STOP.value -> {
                    thread.interrupt()
                    binding.txtView.text = "${currentTime}초"
                    timeState = TimeState.STOP
                }

                TimeState.FINISH.value -> {
                    thread.interrupt()
                    binding.txtView.text = "FINISH"
                    currentTime = 0
                    timeState = TimeState.FINISH
                }
            }
        }
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_handler_timer
        )
        binding.txtView.setText("Timer")
    }

    fun onClickHandlerStart(view: View) {
        if (timeState != TimeState.PLAYING) {
            thread = TimerThread(currentTime, finishTime)
            thread.start()
            binding.btnStart.text = "STOP"
        } else {
            handler.sendEmptyMessage(TimeState.STOP.value)
            binding.btnStart.text = "START"

        }
    }

    fun onClickHandlerFinish(view: View) {
        if (timeState != TimeState.NONE) {
            handler.sendEmptyMessage(TimeState.FINISH.value)
        }
    }

    inner class TimerThread(
        startSecond: Int,
        val finishSecond: Int
    ) : Thread() {
        var startTime = startSecond

        override fun run() {
            super.run()

            while (!isInterrupted) {
                startTime++
                val msg = handler.obtainMessage()
                msg.what = TimeState.PLAYING.value
                msg.arg1 = startTime

                val info = "초"
                msg.obj = info

                handler.sendMessage(msg)
                if (startTime >= finishSecond) {
                    handler.sendEmptyMessage(TimeState.FINISH.value)
                }

                try {
                    sleep(1000)
                } catch (e: InterruptedException) {
                    interrupt()
                }
            }
            timeState == TimeState.FINISH
        }

    }
}




