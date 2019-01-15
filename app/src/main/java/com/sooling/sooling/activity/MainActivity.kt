package com.sooling.sooling.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sooling.sooling.R
import com.sooling.sooling.activity.add_drink.AddHistoryActivity
import com.sooling.sooling.activity.calendar.CalendarActivity
import com.sooling.sooling.activity.setting.SettingActivity
import com.sooling.sooling.activity.wiki.WikiActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        Glide.with(applicationContext)
                .load(R.drawable.icon)
                .apply(RequestOptions().circleCrop())
                .into(iv_main_profile)

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
