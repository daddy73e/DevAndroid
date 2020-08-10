package com.daddy73e.androidkotlin.listener

import com.daddy73e.androidkotlin.model.RecyclerViewItem

interface RecyclerItemClickListener {
    fun onClickListItem(rvItem:RecyclerViewItem)
}