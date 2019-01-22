package com.sooling.sooling.activity.signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import com.sooling.sooling.R
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast
import android.text.Editable
import android.text.TextWatcher
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
    private var imageURL:String? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val intent = intent

        if(intent.hasExtra("imageURL")) {

            imageURL = intent.getStringExtra("imageURL")
            Glide.with(this)
                    .load(imageURL)
                    .apply(RequestOptions().circleCrop())
                    .into(signup_profile)

        } else {
            toast("전달된 이미지가 없습니다.")
        }

        user_name.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                var userName = user_name.text.toString()
                var userMessage = user_message.text.toString()

                if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userMessage)){
                    start_app.visibility = View.VISIBLE
                }
                else {
                    start_app.visibility = View.GONE
                }

            }

            override fun afterTextChanged(s: Editable) {}
        })

        user_message.addTextChangedListener(object : TextWatcher {

//            var userName = user_name.text.toString()
//            var userMessage = user_message.text.toString()

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                var userName = user_name.text.toString()
                var userMessage = user_message.text.toString()

                if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userMessage)){
                    start_app.visibility = View.VISIBLE
                }
                else {
                    start_app.visibility = View.GONE
                }

            }

            override fun afterTextChanged(s: Editable) {
                //입력이 끝났을 때
            }
        })


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