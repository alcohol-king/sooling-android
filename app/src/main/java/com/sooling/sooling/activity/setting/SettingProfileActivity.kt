package com.sooling.sooling.activity.setting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.request.RequestOptions
import com.sooling.sooling.R
import com.sooling.sooling.model.GetCardData
import kotlinx.android.synthetic.main.activity_setting_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import android.animation.PropertyValuesHolder
import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable


class SettingProfileActivity : AppCompatActivity(), View.OnClickListener {
    val pickPhoto = 1
    val takePicture = 2
    lateinit var labelBtns: ArrayList<Button>
    var labelStatus = arrayListOf(false, false, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_profile)

        labelBtns = arrayListOf(
                btn_label_beer, btn_label_soju, btn_label_wine, btn_label_makgeolli
        )

        initView()
    }

    private fun initView() {
        toolbar_title.text = getString(R.string.setting_profile)

        Glide.with(applicationContext)
                .load(R.drawable.icon)
                .apply(RequestOptions().circleCrop())
                .into(iv_setting_profile)

        labelBtns.forEachIndexed { index, btn ->
            btn.setOnClickListener {
                val drinkType = btn.text.toString().substring(0, btn.text.indexOf("\n")).toUpperCase()
                btn.setBackgroundResource(
                        if (!labelStatus[index])
                            GetCardData(this).getDrawable(drinkType)
                        else R.drawable.back_dash
                )

                btn.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        resources.getDrawable(
                                if (!labelStatus[index]) R.drawable.ic_close
                                else R.drawable.ic_plus), null)

                labelStatus[index] = !labelStatus[index]
            }
        }

        ib_back.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ib_back -> finish()
        }
    }
}