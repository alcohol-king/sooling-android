package com.sooling.sooling.util

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import com.sooling.sooling.R
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class GenerateCardCrop(val name: String, val layout: FrameLayout, val download: Boolean) {

    // 화면 전체 캡쳐
    fun captureScreen(window: Window, context: Context) {
        val rootView = window.decorView
        val cardImg = saveCardImg(rootView)

        if (cardImg == null)
            context.toast(context.getString(R.string.err_img_save))
        else if (download) {
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(cardImg))
            context.sendBroadcast(intent)
            context.toast(context.getString(R.string.download_success))
        } else {
            val msg = context.getString(R.string.share_kakao_msg)
            ShareCardWithKakaoTalk(context, cardImg).uploadImg(name + msg)
        }
    }

    fun cropCard(bmp: Bitmap): Bitmap {
        val location = IntArray(2)
        layout.getLocationOnScreen(location)

        val result = Bitmap.createBitmap(
                bmp, location[0], location[1]
                , location[0] + getXdp(240f)
                , location[1] + getYdp(180f)
        )

        if (result != bmp)
            bmp.recycle()

        return result
    }

    // dp를 px로 단위 변환
    fun getXdp(dp: Float): Int {
        return Math.round(
                dp * (Resources.getSystem().displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)
        )
    }

    fun getYdp(dp: Float): Int {
        return Math.round(
                dp * (Resources.getSystem().displayMetrics.ydpi / DisplayMetrics.DENSITY_DEFAULT)
        )
    }

    // 로컬 저장소에 이미지 저장
    fun saveCardImg(view: View): File? {
        val sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val dateTime = Calendar.getInstance().timeInMillis.toString()
        val gallery = File("$sd/YellowCard")

        if (!gallery.isDirectory)
            gallery.mkdirs()

        view.isDrawingCacheEnabled = true

        val bmp = cropCard(Bitmap.createBitmap(view.drawingCache)) // 캐시를 비트맵으로 전환
        val imgFile = File(gallery, "${dateTime}_$name.png")

        view.isDrawingCacheEnabled = false

        try {
            val os = FileOutputStream(imgFile)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, os) // PNG로 변환
            os.flush()
            os.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        return imgFile
    }
}