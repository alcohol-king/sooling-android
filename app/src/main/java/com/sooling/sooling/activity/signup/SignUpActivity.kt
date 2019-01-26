package com.sooling.sooling.activity.signup

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log.d
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kakao.auth.Session
import com.sooling.sooling.R
import com.sooling.sooling.`object`.SignIn
import com.sooling.sooling.`object`.User
import com.sooling.sooling.activity.main.MainActivity
import com.sooling.sooling.service.SignInService
import com.sooling.sooling.util.TransBitmap
import com.sooling.sooling.util.UserDataManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.toast
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class SignUpActivity : AppCompatActivity() {

    internal lateinit var mCompositeDisposable: CompositeDisposable
    private var imageURL: String = ""
    private var userName = ""
    private var userMessage = ""

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val intent = intent

        if (intent.hasExtra("imageURL")) {
            imageURL = intent.getStringExtra("imageURL")
            Glide.with(this)
                    .load(imageURL)
                    .apply(RequestOptions().circleCrop())
                    .into(signup_profile)

        } else {
            toast("전달된 이미지가 없습니다.")
        }

        user_name.setOnClickListener {
            user_name.hint = "닉네임(최대 5자)"
            user_name.backgroundResource = R.drawable.signup_round_conner_white
        }

        user_message.setOnClickListener {
            user_message.hint = "상태말까지 다 채우면 완료(최대 20자)"
            user_message.backgroundResource = R.drawable.signup_round_conner_white
        }

        user_name.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                user_name.backgroundResource = R.drawable.signup_round_conner_gray

                var userName = user_name.text.toString()
                var userMessage = user_message.text.toString()

                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userMessage)) {
                    start_app.visibility = View.VISIBLE
                } else {
                    start_app.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        user_message.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                user_message.backgroundResource = R.drawable.signup_round_conner_gray

                var userName = user_name.text.toString()
                var userMessage = user_message.text.toString()

                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userMessage)) {
                    start_app.visibility = View.VISIBLE
                } else {
                    start_app.visibility = View.GONE
                }

            }

            override fun afterTextChanged(s: Editable) {}
        })

        //가입 버튼 클릭
        start_app.setOnClickListener {
            progress_sign_up.visibility = View.VISIBLE
            val token = Session.getCurrentSession().tokenInfo.accessToken
            val userName = user_name.text.toString()
            val userMessage = user_message.text.toString()

            val signIn = SignIn(token, userName, userMessage, "")

            mCompositeDisposable = CompositeDisposable()

            mCompositeDisposable.add(SignInService.instance.resisterUser(signIn)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext() { res ->
                        if (res.code() == HttpsURLConnection.HTTP_OK) {

                            toast("가입이 완료되었습니다!")
                            d("@@SignIn Response", "" + res.body())
                            setUserInfo(res.body()!!.token, userName, imageURL, userMessage)

                        } else
                            toast("가입에 실패하였습니다.")

                        //로딩 이미지
                        progress_sign_up.visibility = View.INVISIBLE

                    }
                    .subscribe())
        }
    }

    private fun setUserInfo(token: String, userName: String, imageURL: String, userMessage: String) {
        val bmp = BitmapFactory.decodeStream(URL(imageURL).openStream())

        UserDataManager.getInstance(this).saveUserInfo(User(
                token, userName, TransBitmap.bitmapToString(bmp), userMessage
        ))

        val intent = Intent(this@SignUpActivity, MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}