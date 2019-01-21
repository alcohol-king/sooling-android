package com.sooling.sooling.activity.setting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.sooling.sooling.R
import kotlinx.android.synthetic.main.toolbar.*

class SettingCardActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_card)

        initView()
    }

    private fun initView() {
        toolbar_title.text = getString(R.string.setting_card)

        ib_back.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.ib_back -> finish()
        }
    }
}