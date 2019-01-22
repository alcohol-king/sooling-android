package com.sooling.sooling.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.sooling.sooling.R
import com.sooling.sooling.activity.login.LoginActivity
import com.sooling.sooling.activity.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH = 2000

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val mainIntent = Intent(this@SplashActivity, LoginActivity::class.java)
            this@SplashActivity.startActivity(mainIntent)
            this@SplashActivity.finish()

        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}
