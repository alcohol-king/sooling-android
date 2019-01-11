package com.sooling.sooling.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sooling.sooling.R
import com.sooling.sooling.model.DrinkCard
import com.sooling.sooling.util.GetCardData
import kotlinx.android.synthetic.main.item_card.view.*
import org.jetbrains.anko.backgroundResource


class CardListAdapter(val context: Context, val cardList: List<DrinkCard>)
    : RecyclerView.Adapter<CardListAdapter.CardListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): CardListViewHolder {
        val viewGroup = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardListViewHolder(viewGroup)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardListViewHolder, position: Int) {
        val data = GetCardData(context)
        val type = cardList[position].drinkType

        holder.cardBack.backgroundResource = data.getColor(type)
        holder.drinkIcon.setImageResource(data.getDrinkIcon(type))

        holder.cardMsg.text = data.getFirstMsg(type)
        holder.cardMsg2.text = cardList[position].message
        holder.cardMsg3.text = data.getLastMsg(type)
    }

    inner class CardListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardBack: View = itemView.view_card_back
        val cardMsg: TextView = itemView.tv_card_msg1
        val cardMsg2: TextView = itemView.tv_card_msg2
        val cardMsg3: TextView = itemView.tv_card_msg3
        val drinkIcon: ImageView = itemView.iv_main_drink
    }
}