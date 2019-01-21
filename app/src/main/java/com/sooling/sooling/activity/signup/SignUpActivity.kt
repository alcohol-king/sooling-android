package com.sooling.sooling.activity.signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log.d
import android.util.Log.v
import android.view.View
import android.widget.Toast
import com.sooling.sooling.R
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast
import android.text.Editable
import android.text.TextWatcher
import com.sooling.sooling.network.RetrofitBuilder
import com.sooling.sooling.network.SignUpAPIService
import com.google.gson.JsonArray
import com.kakao.auth.Session
import com.sooling.sooling.`object`.SignIn
import com.sooling.sooling.activity.main.MainActivity
import com.sooling.sooling.service.SignInService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.net.ssl.HttpsURLConnection


class SignUpActivity : AppCompatActivity() {

    internal lateinit var mCompositeDisposable: CompositeDisposable

//    private var mCompositeDisposable: CompositeDisposable? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        start_app.visibility = View.VISIBLE

        var userName = user_name.text.toString()
        var userMessage = user_message.text.toString()


//        user_name.addTextChangedListener(object : TextWatcher {
//
//            var userName = user_name.text.toString()
//            var userMessage = user_message.text.toString()
//
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//
//                if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userMessage)){
//                    start_app.visibility = View.VISIBLE
//                }
//                else {
//                    start_app.visibility = View.GONE
//                }
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//
//                if(TextUtils.isEmpty(userMessage)){
//                    start_app.visibility = View.GONE
//                }
//                else {
//                    start_app.visibility = View.VISIBLE
//                }
//            }
//
//            override fun afterTextChanged(s: Editable) {
//                //입력이 끝났을 때
//            }
//        })
//
//        user_message.addTextChangedListener(object : TextWatcher {
//
//            var userName = user_name.text.toString()
//            var userMessage = user_message.text.toString()
//
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if(TextUtils.isEmpty(userName)){
//                    start_app.visibility = View.GONE
//                }
//                else {
//                    start_app.visibility = View.VISIBLE
//                }
//            }
//
//            override fun afterTextChanged(s: Editable) {
//                //입력이 끝났을 때
//            }
//        })


        start_app.setOnClickListener {

            var token = Session.getCurrentSession().tokenInfo.accessToken
            var userName = user_name.text.toString()
            var userMessage = user_message.text.toString()

            val signIn = SignIn(token, userName,userMessage)

            mCompositeDisposable = CompositeDisposable()

            mCompositeDisposable.add(SignInService.instance.resisterUser(signIn)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext({ res ->
                        if (res.code() === HttpsURLConnection.HTTP_OK) {
                            toast("가입이 완료되었습니다!")
                            var intent = Intent(this@SignUpActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            toast("가입에 실패하였습니다.")
                        }
                    })
                    .subscribe())

        }

    }
}