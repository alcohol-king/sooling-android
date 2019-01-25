package com.sooling.sooling.util

import android.content.Context
import android.util.Log
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.ButtonObject
import com.kakao.message.template.ContentObject
import com.kakao.message.template.FeedTemplate
import com.kakao.message.template.LinkObject
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import com.kakao.network.storage.ImageUploadResponse
import com.sooling.sooling.R
import org.jetbrains.anko.toast
import java.io.File


class ShareCardWithKakaoTalk(val context: Context, val imgFile: File) {
    val webUrl = "http://yellowcard-api.herokuapp.com/swagger-ui.html#!/main-controller/mainUsingGET"
    val mobileUrl = "http://yellowcard-api.herokuapp.com/swagger-ui.html#!/main-controller/mainUsingGET"
    val recommendImg = "https://blogfiles.pstatic.net/MjAxOTAxMjNfMTky/MDAxNTQ4MjM5NzExNDI4.THtF7ciCFmBLgr63t22sSi0FiP9M5VRfYqzFpWOlFJ0g.dZAy6Qf-WGg4big_ULpdMtvTww1sEH6ZitC6e6JQYNEg.JPEG.zion830/splash.jpg"
    val service = KakaoLinkService.getInstance()

    constructor(context: Context) : this(context, File(""))

    // 템플릿 생성 후 공유
    fun excute(msg: String, imgUrl: String) {
        if (imgUrl.isNotEmpty()) {
            val params = FeedTemplate
                    .newBuilder(ContentObject.newBuilder(msg, imgUrl,
                            LinkObject.newBuilder().setWebUrl(webUrl)
                                    .setMobileWebUrl(mobileUrl).build())
                            .build())
                    .addButton(ButtonObject(context.getString(R.string.share_kakao_title),
                            LinkObject.newBuilder().setWebUrl(webUrl)
                                    .setMobileWebUrl(mobileUrl).build()))
                    .build()

            service.sendDefault(context, params, object : ResponseCallback<KakaoLinkResponse>() {
                override fun onFailure(errorResult: ErrorResult) {
                    Log.d("카카오링크 오류", errorResult.errorMessage)
                }

                override fun onSuccess(result: KakaoLinkResponse) {
                    Log.d("카카오링크 정보", result.argumentMsg.toString())
                }
            })
        } else {
            context.toast(context.getString(R.string.err_img_upload))
        }
    }

    fun excuteRecommend() {
        excute("나만의 카드를 만들어서 내 주량을 공유해보세요!", recommendImg)
    }

    // 서버에 이미지 업로드
    fun uploadImg(msg: String) {
        service.uploadImage(context, false, imgFile,
                object : ResponseCallback<ImageUploadResponse>() {
                    override fun onFailure(errorResult: ErrorResult) {
                        context.toast(context.getString(R.string.err_img_upload))
                    }

                    override fun onSuccess(result: ImageUploadResponse) {
                        excute(msg, result.original.url)
                    }
                })
    }
}