package com.sooling.sooling.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kakao.auth.Session
import kotlinx.android.synthetic.main.activity_main.*
import com.sooling.sooling.R
import com.sooling.sooling.activity.login.SessionCallback


class MainActivity : AppCompatActivity() {

    private var callback: SessionCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
