package com.sooling.sooling.activity.login

import android.content.ContentValues.TAG
import android.util.Log
import com.kakao.auth.ErrorCode
import com.kakao.auth.ISessionCallback
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.response.model.UserProfile
import com.kakao.util.exception.KakaoException
import com.kakao.network.ApiErrorCode.CLIENT_ERROR_CODE
import com.kakao.usermgmt.callback.MeResponseCallback
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response


internal class SessionCallback : ISessionCallback {

    override fun onSessionOpened() {

        requestMe();

//        UserManagement.requestMe(object : MeResponseCallback() {
//
//            override fun onFailure(errorResult: ErrorResult?) {
//                val message = "failed to get user info. msg=" + errorResult!!
//
//                val result = ErrorCode.valueOf(errorResult.errorCode)
//                if (result === ErrorCode.CLIENT_ERROR_CODE) {
//                    //에러로 인한 로그인 실패
//                    // finish();
//                } else {
//                    //redirectMainActivity();
//                }
//            }
//
//            override fun onSessionClosed(errorResult: ErrorResult) {}
//
//            override fun onNotSignedUp() {
//
//            }
//
//            override fun onSuccess(userProfile: UserProfile) {
//                //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
//                //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
//
//                Log.e("UserProfile", userProfile.toString());
//                Log.e("UserProfile", userProfile.getId() + "");
//
//                val number = userProfile.id
//            }
//        })

    }

    // 세션 실패시
    override fun onSessionOpenFailed(exception: KakaoException) {

    }

    fun requestMe() {
        //유저의 정보를 받아오는 함수

        val keys = ArrayList<String>()
        keys.add("properties.nickname")

        UserManagement.getInstance().me(keys, object : MeV2ResponseCallback() {

            override fun onFailure(errorResult: ErrorResult?) {
                Log.e(TAG, "error message=" + errorResult!!)
                //                super.onFailure(errorResult);
            }

            override fun onSessionClosed(errorResult: ErrorResult) {

                Log.d(TAG, "onSessionClosed1 =$errorResult")
            }


            override fun onSuccess(response: MeV2Response?) {
                if (response != null) {
                    Log.e("UserProfile", response.profileImagePath)
                    Log.d("User@@@@", ""+ response.id)
                }
//                Log.d("UserProfile", "" + response.getId())
            }

        })
    }
}