package com.sooling.sooling.activity.add_drink

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.Button
import com.sooling.sooling.R
import com.sooling.sooling.model.GetCardData
import kotlinx.android.synthetic.main.activity_add_history.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.*
import java.util.*

class AddHistoryActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var drinkBtns: ArrayList<Button>
    lateinit var capacityBtns: ArrayList<Button>
    var drinkBtnStatus = BooleanArray(4)
    var beerBtnStatus = BooleanArray(8)
    var sojuStatus = BooleanArray(8)
    var wineStatus = BooleanArray(8)
    var makgeolliStatus = BooleanArray(8)

    val c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_history)

        drinkBtns = arrayListOf(
                btn_add_beer, btn_add_soju, btn_add_wine, btn_add_makgeolli
        )

        capacityBtns = arrayListOf(
                btn_capacity1, btn_capacity2, btn_capacity3, btn_capacity4,
                btn_capacity5, btn_capacity6, btn_capacity7, btn_capacity8
        )

        initView()
    }

    private fun initView() {
        toolbar_title.text = getString(R.string.all_add_history)
        btn_add_date.text = getDateStr(year, month, day)

        drinkBtns.forEachIndexed { index, btn ->
            btn.setOnClickListener {
                drinkBtnStatus[index] = !drinkBtnStatus[index]
                btn.backgroundDrawable =
                        resources.getDrawable(
                                if (drinkBtnStatus[index]) R.drawable.back_rect_black2
                                else R.drawable.back_rect_gray
                        ) as Drawable
                btn.textColor =
                        if (drinkBtnStatus[index]) Color.WHITE
                        else R.color.colorDarkerGray

                val capacityStr = GetCardData(this).getCapacityArray(btn.text.toString())
                /*capacityBtns.forEachIndexed { index, btn ->
                    btn.text = capacityStr[index]
                    btn.backgroundColor =
                            if (beerBtnStatus[index]) Color.BLACK
                            else Color.TRANSPARENT
                }*/
            }
        }

        capacityBtns.forEachIndexed { index, btn ->
            if (index < 6) {

            } else {

            }
        }

        ib_back.setOnClickListener(this)
        iv_minus.setOnClickListener(this)
        iv_plus.setOnClickListener(this)
        btn_add_date.setOnClickListener(this)
    }

    fun showInputCapacityDialog() {
        alert {
            linearLayout {
                gravity = Gravity.CENTER_HORIZONTAL
                textView("더 많이요? 그러다 죽어요!\n적당한 음주를 권장합니다.ㅜㅜ")
                editText {

                }
                view {
                }
            }
        }.show()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ib_back -> finish()
            R.id.iv_minus -> {
                var count = tv_count.text.toString().toInt()
                if (count > 0) tv_count.text = (--count).toString()
            }
            R.id.iv_plus -> {
                var count = tv_count.text.toString().toInt()
                tv_count.text = (++count).toString()
            }
            R.id.btn_add_date -> {
                val datePicker = DatePickerDialog(this,
                        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                            this.year = year
                            this.month = monthOfYear
                            this.day = dayOfMonth
                            btn_add_date.text = getDateStr(year, month, day)
                        }, year, month, day)

                datePicker.show()
            }
        }
    }

    fun getDateStr(year: Int, month: Int, day: Int): String =
            "" + year + "년 " + (month + 1) + "월 " + day + "일"
}