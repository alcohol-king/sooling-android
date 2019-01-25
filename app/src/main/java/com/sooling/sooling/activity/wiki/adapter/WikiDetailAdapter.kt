package com.sooling.sooling.activity.wiki.adapter

import android.content.Context
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sooling.sooling.R
import com.sooling.sooling.`object`.Drink

class WikiDetailAdapter(val context: Context, val drinkList : ArrayList<Drink>): RecyclerView.Adapter<WikiDetailAdapter.ViewHolder>() {

    interface ItemClick
    {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.wiki_detail_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bind(drinkList[position], context)

        holder?.itemView?.setOnClickListener { v->
            itemClick?.onClick(v, position)
        }

    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val ivDrinkImg = itemView?.findViewById<ImageView>(R.id.iv_drink)
        val tvDescription = itemView?.findViewById<TextView>(R.id.tv_description)
        val tvDrinkName = itemView?.findViewById<TextView>(R.id.tv_drink_name)
        var tvLikeNum = itemView?.findViewById<TextView>(R.id.tv_like_num)
        val tvDrinkPrice = itemView?.findViewById<TextView>(R.id.tv_drink_price)
        val tvDrinkProof = itemView?.findViewById<TextView>(R.id.tv_drink_proof)

        fun bind (drink: Drink, context: Context) {
            ivDrinkImg?.setImageResource(drink.drinkImg)
            tvDescription?.text = drink.description
            tvDrinkName?.text = drink.name
            tvLikeNum?.text = drink.likeNum.toString()
            tvDrinkPrice?.text = drink.price.toString()
            tvDrinkProof?.text = drink.proof.toString()
        }


    }
}