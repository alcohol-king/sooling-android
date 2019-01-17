package com.sooling.sooling.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.view.View
import android.view.Window
import com.sooling.sooling.R
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class GenerateCardCrop(val name: String) {
    // 화면 전체 캡쳐
    fun captureScreen(window: Window, context: Context) {
        val rootView = window.decorView
        val cardImg = saveCardImg(rootView)

        if (cardImg != null) {
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(cardImg))
            context.sendBroadcast(intent)
        } else
            context.toast(context.getString(R.string.err_img_save))
    }

    // 로컬 저장소에 이미지 저장
    fun saveCardImg(view: View): File? {
        val sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val dateTime = Calendar.getInstance().timeInMillis.toString()
        val gallery = File("$sd/YellowCard")

        if (!gallery.isDirectory)
            gallery.mkdirs()

        view.isDrawingCacheEnabled = true

        val bmp = Bitmap.createBitmap(view.drawingCache) // 캐시를 비트맵으로 전환
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