package com.sooling.sooling.activity.add_drink

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sooling.sooling.R
import kotlinx.android.synthetic.main.activity_add_history.*
import java.util.*

class AddHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_history)
        setSupportActionBar(toolbar_main)

        initView()
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.all_add_history)
        // supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        iv_minus.setOnClickListener {
            var count = tv_count.text.toString().toInt()
            if (count > 0)
                tv_count.text = (--count).toString()
        }

        iv_plus.setOnClickListener {
            var count = tv_count.text.toString().toInt()
            tv_count.text = (++count).toString()
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        btn_add_date.text = getDateStr(year, month, day)
        btn_add_date.setOnClickListener {
            val datePicker = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        btn_add_date.text = getDateStr(year, monthOfYear, dayOfMonth)
                    }, year, month, day)

            datePicker.show()
        }
    }

    fun getDateStr(year: Int, month: Int, day: Int): String =
            "" + year + "년 " + (month + 1) + "월 " + day + "일"
}