package com.sooling.sooling.activity.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.kakao.auth.Session
import com.sooling.sooling.R
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    private var callback: SessionCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_test.setOnClickListener {
            Toast.makeText(this,"test", Toast.LENGTH_SHORT).show()
        }

        btn_kakao_login.performClick()

        callback = SessionCallback()
        Session.getCurrentSession().addCallback(callback)
        callback!!.requestMe()
    }
}
