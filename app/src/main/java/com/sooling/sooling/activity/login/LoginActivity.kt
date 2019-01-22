package com.sooling.sooling.activity.login

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import com.kakao.auth.Session
import com.sooling.sooling.R
import kotlinx.android.synthetic.main.activity_login.*
import android.util.Log
import android.util.Log.d
import android.view.View
import com.kakao.auth.ISessionCallback
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.sooling.sooling.activity.signup.SignUpActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private var callback: SessionCallback? = null


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        callback = SessionCallback()
        Session.getCurrentSession().addCallback(callback)

        //토큰 만료시 갱신
        if (Session.getCurrentSession().isOpenable) {
            Session.getCurrentSession().checkAndImplicitOpen()
        }

        btn_real_login.setOnClickListener {

            btn_kakao_login.performClick()
        }

        Log.d("@@token", "토큰 : " + Session.getCurrentSession().tokenInfo.accessToken)
        Log.d("@@token", "토큰 리프레쉬토큰 : " + Session.getCurrentSession().tokenInfo.refreshToken)
        Log.d("@@token", "토큰 파이어데이트 : " + Session.getCurrentSession().tokenInfo.remainingExpireTime)

    }

    private inner class SessionCallback : ISessionCallback {

        override fun onSessionOpened() {

            Log.d("Success Login", "success login")
            requestMe()

        }

        // 세션 실패시
        override fun onSessionOpenFailed(exception: KakaoException) {
            Log.d("Fail Login", "reason : " + exception)

        }

        fun requestMe() {
            //유저의 정보를 받아오는 함수

            val keys = ArrayList<String>()
            keys.add("properties.nickname")
            keys.add("properties.profile_image")

            UserManagement.getInstance().me(keys, object : MeV2ResponseCallback() {

                override fun onFailure(errorResult: ErrorResult?) {
                    Log.e(ContentValues.TAG, "error message=" + errorResult!!.errorMessage)
                }

                override fun onSessionClosed(errorResult: ErrorResult) {

                    Log.d(ContentValues.TAG, "onSessionClosed =${errorResult.errorMessage}")
                }

                override fun onSuccess(response: MeV2Response?) {
                    if (response != null) {
                        d("@@User nickname", "" + response.nickname)
                        d("@@User id", "" + response.id)
                        d("@@User Image URL", ""+response.profileImagePath)
                        redirectSignupActivity(response.profileImagePath)
                    }
                    else
                        toast("서버에 문제가 생겨 접속에 실패하였습니다.")
                }
            })
        }
    }


    protected fun redirectSignupActivity(imageURL : String) {
        val intent = Intent(this, SignUpActivity::class.java)
        intent.putExtra("imageURL",imageURL)
        this@LoginActivity.startActivity(intent)
        this@LoginActivity.finish()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
