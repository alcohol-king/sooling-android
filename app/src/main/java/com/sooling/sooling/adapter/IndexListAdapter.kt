package com.sooling.sooling.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sooling.sooling.R
import com.sooling.sooling.`object`.DrinkCard
import kotlinx.android.synthetic.main.item_circle.view.*


class IndexListAdapter(val context: Context, var list: List<DrinkCard>)
    : RecyclerView.Adapter<IndexListAdapter.IndexListViewHolder>() {
    val indexList = arrayListOf<Boolean>()
    var index = 0

    init {
        list.onEach { indexList.add(false) }

        if (list.isNotEmpty())
            indexList[0] = true
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): IndexListViewHolder {
        val viewGroup = LayoutInflater.from(parent.context).inflate(R.layout.item_circle, parent, false)
        return IndexListViewHolder(viewGroup)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItemIndex(position: Int) {
        indexList[index] = false
        indexList[position] = true
        index = position

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: IndexListViewHolder, position: Int) {
        val asset =
                if (indexList[position]) R.drawable.back_circle_gray3
                else R.drawable.back_circle_gray2
        holder.circleBack.background = context.resources.getDrawable(asset)
    }

    inner class IndexListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val circleBack: View = itemView.item_circle
    }
}