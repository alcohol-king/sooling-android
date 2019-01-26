package com.sooling.sooling.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.sooling.sooling.R
import com.sooling.sooling.model.GetCardData
import kotlinx.android.synthetic.main.item_add_history.view.*


class HistoryListAdapter(val context: Context, var strList: ArrayList<String>)
    : RecyclerView.Adapter<HistoryListAdapter.HistoryListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): HistoryListViewHolder {
        val viewGroup = LayoutInflater.from(parent.context).inflate(R.layout.item_add_history, parent, false)
        return HistoryListViewHolder(viewGroup)
    }

    override fun getItemCount(): Int = strList.size

    override fun onBindViewHolder(holder: HistoryListViewHolder, position: Int) = holder.bind(position)

    fun addItem(item: String) {
        strList.add(item)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        strList.removeAt(position)
        notifyDataSetChanged()
    }

    fun setItem(position: Int, item: String) {
        strList[position] = item
        notifyDataSetChanged()
    }

    fun setPeopleCount(count: Int) {
        strList.forEachIndexed { index, s ->
            val peopleCount = if (count == 0) "혼자서" else "${count}명과"
            strList[index] = peopleCount + s.substring(s.indexOf("명과") + 2, s.length)
        }

        notifyDataSetChanged()
    }

    fun getItemIndex(type: String): Int {
        strList.forEachIndexed { index, s -> if (s.contains(type)) return index }
        return -1
    }

    inner class HistoryListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.tv_history
        val closeBtn: ImageButton = itemView.ib_history_close

        fun bind(position: Int) {
            title.text = strList[position]

            closeBtn.setOnClickListener {
                deleteItem(position)
            }
        }
    }
}