package com.sooling.sooling.activity.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sooling.sooling.R
import com.sooling.sooling.`object`.DrinkCard
import com.sooling.sooling.`object`.User
import com.sooling.sooling.activity.add_drink.AddHistoryActivity
import com.sooling.sooling.activity.setting.SettingActivity
import com.sooling.sooling.activity.wiki.WikiActivity
import com.sooling.sooling.adapter.CardListAdapter
import com.sooling.sooling.adapter.IndicatorAdapter
import com.sooling.sooling.custom_view.ShareDialog
import com.sooling.sooling.util.RecyclerItemClickListener
import com.sooling.sooling.util.TransBitmap
import com.sooling.sooling.util.UserDataManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity(), View.OnClickListener {
    val requestPermission = 0
    val codeSetting = 1
    var index = 0
    lateinit var adapter: CardListAdapter
    lateinit var user: User
    var cardList = listOf<DrinkCard>(
            DrinkCard("BEER", "500cc까지는 즐기면서"),
            DrinkCard("SOJU", "3잔까지는 멀쩡하게"),
            DrinkCard("WINE", "1병까지는 즐기면서"),
            DrinkCard("MAKGEOLLI", "1병까지는 즐기면서")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        askForPermission()
    }

    private fun initInfo() {
        user = UserDataManager.getInstance(this).getUserInfo()
        Glide.with(applicationContext)
                .load(TransBitmap.stringToBitMap(user.imgUrl))
                .apply(RequestOptions().circleCrop())
                .into(iv_main_profile)

        tv_main_name.text = user.name
        tv_main_msg.text = user.msg
    }

    private fun initView() {
        initInfo()

        adapter = CardListAdapter(this, cardList)
        rv_main_card.adapter = adapter
        rv_main_card.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false
        )

        if (cardList.size < 2) rv_main_index.visibility = View.INVISIBLE

        val indexAdapter = IndicatorAdapter(this, cardList)
        rv_main_index.adapter = indexAdapter
        rv_main_index.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false
        )

        // 카드 리스트에 indicator 추가
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rv_main_card)
        rv_main_card.onFlingListener = snapHelper

        rv_main_card.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                index = (recyclerView.layoutManager as LinearLayoutManager)
                        .findFirstVisibleItemPosition()
                indexAdapter.setItemIndex(index)
            }
        })

        rv_main_index.addOnItemTouchListener(
                RecyclerItemClickListener(applicationContext, rv_main_index,
                        object : RecyclerItemClickListener.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                rv_main_card.smoothScrollToPosition(position)
                            }
                        })
        )

        btn_main_capacity.setOnClickListener(this)
        btn_main_calendar.setOnClickListener(this)
        btn_main_wiki.setOnClickListener(this)
        btn_main_setting.setOnClickListener(this)
        btn_main_share.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_main_capacity -> startActivity<AddHistoryActivity>()
            R.id.btn_main_calendar -> alert {
                title = "송구합니다..."
                message = "캘린더는 아직 개발 중입니다...\n다음 업데이트를 기다려주십쇼."
                positiveButton("납득", onClicked = {})
            }.show()
            R.id.btn_main_wiki -> startActivity<WikiActivity>()
            R.id.btn_main_setting -> startActivityForResult<SettingActivity>(codeSetting)
            R.id.btn_main_share -> {
                ShareDialog(this, cardList[index], user.name).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == codeSetting)
            initInfo()
    }

    // 권한 요청
    private fun askForPermission() {
        val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
        ActivityCompat.requestPermissions(this, permissions, requestPermission)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == requestPermission) {
            for (i in permissions.indices) {
                if (permissions[i] == Manifest.permission.WRITE_EXTERNAL_STORAGE ||
                        permissions[i] == Manifest.permission.CAMERA) {

                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        toast(R.string.permission_denied_msg)
                        finish()
                    }
                }
            }
        }
    }
}
