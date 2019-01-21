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
import com.sooling.sooling.model.DrinkWikiMain

class WikiAdapter(val context: Context, val drinkWikiList : ArrayList<DrinkWikiMain>): RecyclerView.Adapter<WikiAdapter.ViewHolder>() {

    interface ItemClick
    {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.wiki_grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return drinkWikiList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bind(drinkWikiList[position], context)

        holder?.itemView?.setOnClickListener { v->
            itemClick?.onClick(v, position)
        }

    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val alcoholImg = itemView?.findViewById<ImageView>(R.id.alcohol_img)
        val alcoholName = itemView?.findViewById<TextView>(R.id.alcohol_name)

        fun bind (drink: DrinkWikiMain, context: Context) {
//            if (alcohol.name == "소주" ) {
////                val resourceId = context.resources.getIdentifier(alcohol.img, "drawable", context.packageName)
//                alcoholImg?.setImageResource(R.drawable.soju_img)
//            }
//            else if (alcohol.name == "맥주") {
//                alcoholImg?.setImageResource(R.drawable.beer_img)
//            }
//            else if (alcohol.name == "막걸리") {
//                alcoholImg?.setImageResource(R.drawable.makgulri_img)
//            }
//
//            else {
//                alcoholImg?.setImageResource(R.drawable.wine_img)
//            }

            alcoholImg?.setImageResource(drink.img)

            /* 나머지 TextView와 String 데이터를 연결한다. */
            alcoholName?.text = drink.name

        }


    }
}