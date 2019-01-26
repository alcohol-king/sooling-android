package com.sooling.sooling.activity.setting

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log.d
import android.view.View
import android.widget.Button
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sooling.sooling.R
import com.sooling.sooling.`object`.Label
import com.sooling.sooling.model.GetCardData
import com.sooling.sooling.util.TransBitmap
import com.sooling.sooling.util.UserDataManager
import kotlinx.android.synthetic.main.activity_setting_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast


class SettingProfileActivity : AppCompatActivity(), View.OnClickListener {
    val pickPhoto = 1
    lateinit var labelBtns: ArrayList<Button>
    lateinit var manager: UserDataManager
    var labels: ArrayList<Label> = arrayListOf(
            Label("Beer", 3.0f, true),
            Label("Soju", 3.0f, false),
            Label("Wine", 2.0f, false),
            Label("Makgeolli", 0.0f, false)
    )

    var labelStatus = arrayListOf(true, false, false, false)
    var bmp: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_profile)

        manager = UserDataManager.getInstance(this)
        labelBtns = arrayListOf(
                btn_label_beer, btn_label_soju, btn_label_wine, btn_label_makgeolli
        )

        initView()
    }

    private fun initView() {
        toolbar_title.text = getString(R.string.setting_profile)

        val userData = manager.getUserInfo()
        edit_setting_name.setText(userData.name)
        edit_setting_state.setText(userData.msg)

        // 라벨 초기화
        labelBtns.forEachIndexed { index, btn ->
            btn.text = "${labels[index].type}\n${labels[index].level}"
            btn.setBackgroundResource(
                    if (labels[index].isSelected)
                        GetCardData(this).getDrawable(labels[index].type)
                    else R.drawable.back_dash
            )

            btn.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    resources.getDrawable(
                            if (!labels[index].isSelected) R.drawable.ic_close
                            else R.drawable.ic_plus), null)
        }

        Glide.with(applicationContext)
                .load(TransBitmap.stringToBitMap(userData.imgUrl))
                .apply(RequestOptions().circleCrop())
                .into(iv_setting_profile)

        labelBtns.forEachIndexed { index, btn ->
            btn.setOnClickListener {
                val drinkType = btn.text.toString()
                        .substring(0, btn.text.indexOf("\n")).toUpperCase()
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
        ib_setting_img.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickPhoto && resultCode == RESULT_OK) {
            Glide.with(applicationContext)
                    .load(data?.data)
                    .apply(RequestOptions().circleCrop())
                    .into(iv_setting_profile)

            bmp = MediaStore.Images.Media.getBitmap(contentResolver, data?.data)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ib_back -> showSaveDlg()
            R.id.ib_setting_img -> {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
                startActivityForResult(intent, pickPhoto)
            }
        }
    }

    override fun onBackPressed() {
        showSaveDlg()
    }

    fun checkName(): Boolean {
        if (edit_setting_name.text.isEmpty()) {
            toast(R.string.setting_save_err1)
            return false
        } else
            return true
    }

    fun showSaveDlg() {
        alert {
            title = getString(R.string.setting_save)
            message = getString(R.string.setting_save_msg)
            positiveButton(getString(R.string.all_ok), onClicked = {
                if (checkName()) {
                    manager.saveUserName(edit_setting_name.text.toString())
                    manager.saveUserMsg(edit_setting_state.text.toString())
                    if (bmp != null)
                        manager.saveUserImg(TransBitmap.bitmapToString(bmp as Bitmap))

                    finish()
                }
            })
            negativeButton(getString(R.string.all_cancel), onClicked = { finish() })
        }.show()
    }
}