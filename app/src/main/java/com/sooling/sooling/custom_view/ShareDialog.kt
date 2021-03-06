package com.sooling.sooling.custom_view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import com.sooling.sooling.R
import com.sooling.sooling.`object`.DrinkCard
import com.sooling.sooling.model.GetCardData
import com.sooling.sooling.util.GenerateCardCrop
import kotlinx.android.synthetic.main.dialog_share.*
import org.jetbrains.anko.backgroundResource


class ShareDialog(context: Context, val drinkCard: DrinkCard, val name: String)
    : Dialog(context, R.style.ShareDialogTheme), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_share)
        window?.setBackgroundDrawableResource(R.color.colorDialog)

        initView()
    }

    private fun initView() {
        val data = GetCardData(context)
        view_card_back.backgroundResource = data.getColor(drinkCard.drinkType)
        tv_card_msg1.text = data.getFirstMsg(drinkCard.drinkType)
        tv_card_msg2.text = drinkCard.message
        tv_share_name.text = "By. $name"

        ib_share_close.setOnClickListener(this)
        ib_download.setOnClickListener(this)
        ib_share_kakao.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ib_share_close -> this.dismiss()
            R.id.ib_download, R.id.ib_share_kakao -> {
                GenerateCardCrop(name, layout_dialog_card, view.id == R.id.ib_download)
                        .captureScreen(window, context)
                dismiss()
            }
        }
    }
}