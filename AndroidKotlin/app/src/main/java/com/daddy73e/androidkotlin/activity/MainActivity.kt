package com.daddy73e.androidkotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daddy73e.androidkotlin.R
import com.daddy73e.androidkotlin.adapter.MainListAdapter
import com.daddy73e.androidkotlin.databinding.ActivityMainBinding
import com.daddy73e.androidkotlin.listener.RecyclerItemClickListener
import com.daddy73e.androidkotlin.model.RecyclerViewItem
import com.daddy73e.androidkotlin.mvvm.MVVMActivity


/* 기본, 코틀린에서 RecyclerView 만들기 */

class MainActivity : AppCompatActivity() , RecyclerItemClickListener{

    private lateinit var binding:ActivityMainBinding
    private var itemList = ArrayList<RecyclerViewItem>()

    private lateinit var itemListAdapter: MainListAdapter
    private lateinit var itemListManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        setUpRecyclerView()
        setUpItems()
    }

    private fun setUpRecyclerView() {
        itemListAdapter = MainListAdapter(itemList)
        itemListManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = itemListManager
            adapter = itemListAdapter
        }

        itemListAdapter.setItemClickListener(this)
    }

    private fun setUpItems() {
        itemList.add(RecyclerViewItem("01", "RxKotlin 예제"))
        itemList.add(RecyclerViewItem("02", "MVVM 디자인패턴 예제"))
        itemList.add(RecyclerViewItem("03", "Handler 예제"))
        itemList.add(RecyclerViewItem("04", "AsyncTask 예제"))
        itemList.add(RecyclerViewItem("05", "코루틴 예제"))
        itemListAdapter.notifyDataSetChanged()
    }


    override fun onClickListItem(rvItem: RecyclerViewItem) {
        var intent: Intent? = null

        when (rvItem.index) {
            "01" -> { intent = Intent(this, RxKotlinActivity::class.java) }
            "02" -> { intent = Intent(this, MVVMActivity::class.java) }
            "03" -> { intent = Intent(this, HandlerTimerActivity::class.java) }
            "04" -> { intent = Intent(this, AsyncTaskActivity::class.java) }
            "05" -> { intent = Intent(this, CoroutineActivity::class.java) }
        }

        startActivity(intent)
    }
}