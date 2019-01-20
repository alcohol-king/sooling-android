package com.sooling.sooling.activity.add_drink

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Button
import com.sooling.sooling.R
import com.sooling.sooling.adapter.HistoryListAdapter
import com.sooling.sooling.model.GetCardData
import kotlinx.android.synthetic.main.activity_add_history.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class AddHistoryActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var drinkBtns: ArrayList<Button>
    lateinit var capacityBtns: ArrayList<Button>
    var drinkType = "맥주"
    var drinkIndex = 0
    var capacityStrs = Array(8) { "" }
    var drinkBtnIndex = arrayListOf(-1, -1, -1, -1)

    val c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_history)

        drinkBtns = arrayListOf(btn_add_beer, btn_add_soju, btn_add_wine, btn_add_makgeolli)
        capacityBtns = arrayListOf(
                btn_capacity1, btn_capacity2, btn_capacity3, btn_capacity4,
                btn_capacity5, btn_capacity6, btn_capacity7, btn_capacity8
        )

        initView()
    }

    private fun initView() {
        capacityStrs = GetCardData(this).getCapacityArray(drinkType)
        capacityBtns.forEachIndexed { index, btn ->
            btn.text = capacityStrs[index]
        }

        toolbar_title.text = getString(R.string.all_add_history)
        btn_add_date.text = getDateStr(year, month, day)

        val strList = arrayListOf<String>()
        val adapter = HistoryListAdapter(this, strList)
        rv_add_history.adapter = adapter
        rv_add_history.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
        )

        drinkBtns.forEachIndexed { index, btn ->
            btn.setOnClickListener {
                drinkType = btn.text.toString()
                capacityStrs = GetCardData(this).getCapacityArray(drinkType)

                if (this.drinkIndex != index) {
                    drinkBtns[drinkIndex].setTextColor(resources.getColor(R.color.colorDarkerGray))
                    drinkBtns[drinkIndex].setBackgroundResource(R.drawable.back_rect_gray)

                    drinkBtns[index].setTextColor(Color.WHITE)
                    drinkBtns[index].setBackgroundResource(R.drawable.back_rect_black2)

                    // 술 용량 버튼 초기화
                    capacityBtns[drinkIndex].setBackgroundColor(Color.WHITE)
                    capacityBtns[drinkIndex].setTextColor(resources.getColor(R.color.colorDarkerGray))

                    drinkIndex = index
                }

                capacityBtns.forEachIndexed { index, btn ->
                    btn.text = capacityStrs[index]

                    if (index == drinkBtnIndex[drinkIndex]) {
                        capacityBtns[drinkIndex].setBackgroundColor(Color.BLACK)
                        capacityBtns[drinkIndex].setTextColor(Color.WHITE)
                    }
                }
            }
        }

        capacityBtns.forEachIndexed { index, btn ->
            btn.setOnClickListener {
                capacityBtns[index].setBackgroundColor(Color.WHITE)
                capacityBtns[index].setTextColor(resources.getColor(R.color.colorDarkerGray))

                btn.setTextColor(Color.WHITE)
                btn.setBackgroundColor(Color.BLACK)

                drinkBtnIndex[drinkIndex] = index
            }
        }

        ib_back.setOnClickListener(this)
        iv_minus.setOnClickListener(this)
        iv_plus.setOnClickListener(this)
        btn_add_date.setOnClickListener(this)
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

    fun getHistoryStr(people: Int): String {
        val peopleCount = if (people > 0) "$people 명과" else "혼자서"

        return "$peopleCount $drinkType ${capacityStrs[drinkBtnIndex[drinkIndex]]} 마셨어요."
    }
}