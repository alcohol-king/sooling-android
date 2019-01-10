package com.sooling.sooling.activity.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sooling.sooling.R
import com.sooling.sooling.activity.add_drink.AddHistoryActivity
import com.sooling.sooling.activity.CalendarActivity
import com.sooling.sooling.activity.SettingActivity
import com.sooling.sooling.activity.WikiActivity
import com.sooling.sooling.adapter.CardListAdapter
import com.sooling.sooling.model.DrinkCard
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var adapter: CardListAdapter
    var cardList = listOf<DrinkCard>(
            DrinkCard("BEER", "2000cc까지는 즐기면서"),
            DrinkCard("SOJU", "2000cc까지는 즐기면서"),
            DrinkCard("WINE", "2000cc까지는 즐기면서"),
            DrinkCard("MAKGEOLLI", "2000cc까지는 즐기면서")
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