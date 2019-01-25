package com.sooling.sooling.activity.wiki

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log.d
import com.sooling.sooling.R
import com.sooling.sooling.`object`.Drink
import com.sooling.sooling.activity.wiki.adapter.WikiDetailAdapter
import kotlinx.android.synthetic.main.activity_wiki.*
import kotlinx.android.synthetic.main.activity_wiki_detail.*
import org.jetbrains.anko.toast

class WikiDetailActivity : AppCompatActivity() {

//    private var drinkType: Int? = null
//    private var wikiDetailAdapter = null
//    private var layoutManager = null



    private var sojuList = arrayListOf<Drink>(
            Drink("대한민국 청정지역에서만 선별된 대나무 숯으로 4번 걸러 더 깨끗한 목넘김을 가진 소주", 0, "",
                    "참이슬 Fresh",100, 1700, 17.2F, R.drawable.soju_fresh_img),
            Drink("숙취 유발 물질을 깨끗하게 제거하고 레귤러 대비 높은 도수로, 깊고 진한 맛을 구현해 소비자들에게 꾸준히 사랑받는 소주", 0, "",
                    "참이슬 Classic",100, 1700, 20.1F, R.drawable.soju_original_img),
            Drink("세계 최초 알칼리환원수로 만든 소주", 0, "",
                    "처음처럼",100, 1700, 17F, R.drawable.soju_like_first_img),
            Drink("저도수 + 숙취 해소에 집중하여 생산하는 소주", 0, "",
                    "화이트",100, 1700, 19F, R.drawable.soju_white_img)
    )

    private var beerList = arrayListOf<Drink>(
            Drink("카스 프레시는 비열처리공법으로 맥주의 신선하고 톡 쏘는 맛을 더욱 향상시켰습니다.", 0, "",
                    "카스 프레쉬",100, 2700, 4.5F, R.drawable.beer_cass_fresh_img),
            Drink("오리지널 그래비티 공법이란 비가수 공법으로 발효한 맥주원액에 물을 타지 않고 발효원액 그대로 제품을 담아내는 제조방법이다. 풍부한 거품과 풍부한 맛이 특징", 0, "",
                    "클라우드",100, 3500, 5F, R.drawable.beer_cloud_img),
            Drink("세계 최초 알칼리환원수로 만든 소주", 0, "",
                    "하이트 엑스트라 콜드",100, 2700, 4.5F, R.drawable.beer_hite_img),
            Drink("숙성에서부터 여과까지 생산 전 공정을 맥주가 얼기 직전, 영하의 온도에서 제조하여 시원하고 청량한 페일라거 맥주 본연의 맛을 극대화", 0, "",
                    "맥스",100, 3500, 4.5F, R.drawable.beer_max_img)
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wiki_detail)

        var intent = intent

        if (intent.hasExtra("drink_type")) {
            var drinkType = intent.getIntExtra("drink_type",0)

            when(drinkType){
                1 -> setView(sojuList)
                2 -> setView(beerList)
                3 -> setView(sojuList)
                4 -> setView(beerList)
            }

            d("drinkType", "" + drinkType)
        }
        else{
            toast("오류가 발생하였습니다.")
        }


    }

    private fun setView(drinkList : ArrayList<Drink>){

        var wikiDetailAdapter = WikiDetailAdapter(this, drinkList)
        wiki_detail_recyclerview.adapter = wikiDetailAdapter

        var layoutManager = LinearLayoutManager(this)
        wiki_detail_recyclerview.layoutManager = layoutManager
        wiki_detail_recyclerview.setHasFixedSize(true)

    }
}