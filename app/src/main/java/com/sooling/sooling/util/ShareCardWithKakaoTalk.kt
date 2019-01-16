package com.sooling.sooling.util

import android.content.Context
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import com.kakao.network.storage.ImageUploadResponse
import org.jetbrains.anko.toast
import java.io.File


class ShareCardWithKakaoTalk(val context: Context) {
    val webUrl = "http://yellowcard-api.herokuapp.com/swagger-ui.html#!/main-controller/mainUsingGET"

    fun excute(msg: String, imgUrl: String) {

    }

    // 서버에 이미지 업로드
    fun uploadImg(imgFile: File) {
        KakaoLinkService.getInstance().uploadImage(context, false, imgFile,
                object : ResponseCallback<ImageUploadResponse>() {
                    override fun onFailure(errorResult: ErrorResult) {
                    }

                    override fun onSuccess(result: ImageUploadResponse) {
                        context.toast("카카오톡 공유 중 오류가 발생했습니다.")
                    }
                })
    }
}