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

    var count = 1
    val c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    lateinit var adapter: HistoryListAdapter

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
        adapter = HistoryListAdapter(this, strList)
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

                    drinkIndex = index
                }

                capacityBtns.forEachIndexed { index, btn ->
                    btn.text = capacityStrs[index]
                    btn.setBackgroundColor(Color.WHITE)
                    btn.setTextColor(resources.getColor(R.color.colorDarkerGray))

                    if (index == drinkBtnIndex[drinkIndex]) {
                        btn.setBackgroundColor(Color.BLACK)
                        btn.setTextColor(Color.WHITE)
                    }
                }
            }
        }

        capacityBtns.forEachIndexed { index, btn ->
            btn.setOnClickListener {
                // 이전 아이템 선택 해제
                if (drinkBtnIndex[drinkIndex] > -1) {
                    capacityBtns[drinkBtnIndex[drinkIndex]].setBackgroundColor(Color.WHITE)
                    capacityBtns[drinkBtnIndex[drinkIndex]].setTextColor(resources.getColor(R.color.colorDarkerGray))
                }

                btn.setTextColor(Color.WHITE)
                btn.setBackgroundColor(Color.BLACK)

                drinkBtnIndex[drinkIndex] = index

                setHistoryList()
            }
        }

        ib_back.setOnClickListener(this)
        iv_minus.setOnClickListener(this)
        iv_plus.setOnClickListener(this)
        btn_add_date.setOnClickListener(this)
        btn_add_finish.setOnClickListener(this)
    }

    fun setHistoryList() {
        if (adapter.getItemIndex(drinkType) > -1) {
            adapter.setItem(adapter.getItemIndex(drinkType), getHistoryStr())
        } else {
            adapter.addItem(getHistoryStr())
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ib_back -> finish()
            R.id.iv_minus, R.id.iv_plus -> {
                count = tv_count.text.toString().toInt()

                if (count > 0) {
                    tv_count.text =
                            if (view.id == R.id.iv_minus) (--count).toString()
                            else (++count).toString()
                }

                adapter.setPeopleCount(count)
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
            R.id.btn_add_finish -> {
                finish()
            }
        }
    }

    fun getDateStr(year: Int, month: Int, day: Int): String =
            "" + year + "년 " + (month + 1) + "월 " + day + "일"

    fun getHistoryStr(): String {
        val peopleCount = if (count == 0) "혼자서" else "${count}명과"

        return "$peopleCount $drinkType ${capacityStrs[drinkBtnIndex[drinkIndex]]} 마셨어요."
    }
}