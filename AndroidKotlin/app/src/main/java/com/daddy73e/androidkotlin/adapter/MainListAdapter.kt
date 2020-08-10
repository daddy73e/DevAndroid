package com.daddy73e.androidkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daddy73e.androidkotlin.R
import com.daddy73e.androidkotlin.listener.RecyclerItemClickListener
import com.daddy73e.androidkotlin.model.RecyclerViewItem

class MainListAdapter(private val dataList: ArrayList<RecyclerViewItem>) :
    RecyclerView.Adapter<MainListAdapter.ItemHolder>() {

    companion object {
        var clickListener: RecyclerItemClickListener? = null
    }

    fun setItemClickListener(listener: RecyclerItemClickListener) {
        clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MainListAdapter.ItemHolder {

        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_main_list_item, parent, false)
        return ItemHolder(rootView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = dataList[position]

        holder.viewIndex.text = item.index
        holder.viewTitle.text = item.title

        holder.itemView.setOnClickListener {
            if(clickListener != null) {
                clickListener?.onClickListItem(item)
            }
        }
    }

    override fun getItemCount() = dataList.size



    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewIndex =  itemView.findViewById<TextView>(R.id.txtIndex)
        val viewTitle =  itemView.findViewById<TextView>(R.id.txtTitle)
    }
}