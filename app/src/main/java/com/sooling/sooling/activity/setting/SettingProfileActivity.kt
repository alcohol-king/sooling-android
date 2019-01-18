package com.sooling.sooling.activity.setting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sooling.sooling.R
import kotlinx.android.synthetic.main.activity_setting_profile.*
import kotlinx.android.synthetic.main.toolbar.*

class SettingProfileActivity : AppCompatActivity(), View.OnClickListener {
    val pickPhoto = 1
    val takePicture = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_profile)

        initView()
    }

    private fun initView() {
        toolbar_title.text = getString(R.string.setting_profile)

        Glide.with(applicationContext)
                .load(R.drawable.icon)
                .apply(RequestOptions().circleCrop())
                .into(iv_setting_profile)

        ib_back.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.ib_back -> finish()
        }
    }
}