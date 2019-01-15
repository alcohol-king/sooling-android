package com.sooling.sooling.activity.wiki

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.sooling.sooling.R
import com.sooling.sooling.activity.wiki.adapter.WikiAdapter
import com.sooling.sooling.model.AlcoholWiki
import kotlinx.android.synthetic.main.activity_wiki.*

class WikiActivity : AppCompatActivity() {

    private var wikiList = arrayListOf<AlcoholWiki>(
            AlcoholWiki("소주", R.drawable.soju_img),
            AlcoholWiki("맥주", R.drawable.beer_img),
            AlcoholWiki("막걸리", R.drawable.makgulri_img),
            AlcoholWiki("와인", R.drawable.wine_img)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wiki)

        val wikiAdapter = WikiAdapter(this, wikiList)
        wiki_recyclerview.adapter = wikiAdapter

        val layoutManager = GridLayoutManager(this,2)
        wiki_recyclerview.layoutManager = layoutManager
        wiki_recyclerview.setHasFixedSize(true)

        wikiAdapter.itemClick = object: WikiAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@WikiActivity, WikiDetailActivity::class.java)
                startActivity(intent)
            }
        }

        wikiAdapter.notifyDataSetChanged()

    }
}