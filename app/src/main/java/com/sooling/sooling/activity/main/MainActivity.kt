package com.sooling.sooling.activity.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sooling.sooling.R
import com.sooling.sooling.activity.calendar.CalendarActivity
import com.sooling.sooling.activity.setting.SettingActivity
import com.sooling.sooling.activity.WikiActivity
import com.sooling.sooling.activity.add_drink.AddHistoryActivity
import com.sooling.sooling.adapter.CardListAdapter
import com.sooling.sooling.adapter.IndexListAdapter
import com.sooling.sooling.`object`.DrinkCard
import com.sooling.sooling.util.RecyclerItemClickListener
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import android.support.v7.widget.RecyclerView


class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var adapter: CardListAdapter
    var cardList = listOf<DrinkCard>(
            DrinkCard("BEER", "500cc까지는 즐기면서"),
            DrinkCard("SOJU", "3잔까지는 멀쩡하게"),
            DrinkCard("WINE", "1병까지는 즐기면서"),
            DrinkCard("MAKGEOLLI", "1병까지는 즐기면서")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        Glide.with(applicationContext)
                .load(R.drawable.icon_2x)
                .apply(RequestOptions().circleCrop())
                .into(iv_main_profile)

        adapter = CardListAdapter(this, cardList)
        rv_main_intro.adapter = adapter
        rv_main_intro.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false
        )

        if (cardList.size < 2) rv_main_index.visibility = View.INVISIBLE

        val indexAdapter = IndexListAdapter(this, cardList)
        rv_main_index.adapter = indexAdapter
        rv_main_index.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false
        )

        rv_main_intro.addOnItemTouchListener(
                RecyclerItemClickListener(applicationContext, rv_main_intro,
                        object : RecyclerItemClickListener.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                rv_main_intro.smoothScrollToPosition(position)
                            }
                        })
        )

        // 카드 리스트에 indicator 추가
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rv_main_intro)
        rv_main_intro.onFlingListener = snapHelper

        rv_main_intro.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                indexAdapter.setItemIndex(
                        (recyclerView.layoutManager as LinearLayoutManager)
                                .findFirstVisibleItemPosition()
                )
            }
        })

        btn_main_capacity.setOnClickListener(this)
        btn_main_calendar.setOnClickListener(this)
        btn_main_wiki.setOnClickListener(this)
        btn_main_setting.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_main_capacity -> startActivity<AddHistoryActivity>()
            R.id.btn_main_calendar -> startActivity<CalendarActivity>()
            R.id.btn_main_wiki -> startActivity<WikiActivity>()
            R.id.btn_main_setting -> startActivity<SettingActivity>()
        }
    }
}
