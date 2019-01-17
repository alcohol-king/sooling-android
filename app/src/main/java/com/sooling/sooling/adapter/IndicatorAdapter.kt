package com.sooling.sooling.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.sooling.sooling.R
import com.sooling.sooling.`object`.DrinkCard
import com.sooling.sooling.`object`.ID
import org.jetbrains.anko.*


class IndicatorAdapter(val context: Context, var cardList: List<DrinkCard>)
    : RecyclerView.Adapter<IndicatorAdapter.IndicatorViewHolder>() {
    val indexList = Array(cardList.size) { false }
    var index = 0 // 선택된 item의 index

    init {
        if (cardList.isNotEmpty())
            indexList[0] = true
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): IndicatorViewHolder {
        return IndicatorViewHolder(
                IndicatorUI().createView(AnkoContext.create(parent.context, parent))
        )
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    fun setItemIndex(position: Int) {
        indexList[index] = false
        indexList[position] = true
        index = position

        notifyDataSetChanged()
    }

    inner class IndicatorUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            val itemView = with(ui) {
                linearLayout {
                    lparams(width = wrapContent, height = dip(10))

                    view {
                        id = ID.dots
                    }.lparams(width = dip(10)) {
                        rightMargin = dip(5)
                    }
                }
            }

            return itemView
        }
    }

    override fun onBindViewHolder(holder: IndicatorViewHolder, position: Int) {
        val asset = context.resources.getDrawable(
                if (indexList[position]) R.drawable.back_circle_gray3
                else R.drawable.back_circle_gray2
        )

        holder.bind(asset)
    }

    inner class IndicatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view: View = itemView.find(ID.dots)

        fun bind(asset: Drawable) {
            view.background = asset
        }
    }
}