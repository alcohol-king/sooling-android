package com.sooling.sooling.activity.setting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.sooling.sooling.R

class SettingCardActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_card)
    }

    override fun onClick(view: View?) {
    }
}