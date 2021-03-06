package com.sooling.sooling.activity.setting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import com.sooling.sooling.R
import com.sooling.sooling.util.ShareCardWithKakaoTalk
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity


class SettingActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initView()
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_title.text = getString(R.string.all_setting)

        // 추천 버튼에 색상 지정
        val span = SpannableStringBuilder(getString(R.string.setting_friend))
        span.setSpan(ForegroundColorSpan(resources.getColor(R.color.colorBeer))
                , 4, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        btn_recommend.setText(span, TextView.BufferType.SPANNABLE)

        ib_back.setOnClickListener(this)
        btn_profile.setOnClickListener(this)
        btn_card.setOnClickListener(this)
        btn_recommend.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ib_back -> showSaveDialog()
            R.id.btn_profile -> startActivity<SettingProfileActivity>()
            R.id.btn_card -> startActivity<SettingCardActivity>()
            R.id.btn_recommend -> ShareCardWithKakaoTalk(this).excuteRecommend()
        }
    }

    override fun onDestroy() {
        showSaveDialog()

        super.onDestroy()
    }

    fun showSaveDialog() {
        alert {
            title = getString(R.string.setting_save)
            message = getString(R.string.setting_save_msg)
            positiveButton(getString(R.string.all_ok), onClicked = {})
            negativeButton(getString(R.string.all_cancel), onClicked = { finish() })
        }.show()
    }
}