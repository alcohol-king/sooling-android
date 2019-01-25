package com.sooling.sooling.activity.wiki

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.util.Log.d
import android.view.View
import com.sooling.sooling.R
import com.sooling.sooling.activity.wiki.adapter.WikiAdapter
import com.sooling.sooling.model.DrinkWikiMain
import com.sooling.sooling.service.DrinkService
import com.sooling.sooling.util.UserDataManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_wiki.*
import org.jetbrains.anko.toast
import javax.net.ssl.HttpsURLConnection

class WikiActivity : AppCompatActivity() {

    internal lateinit var mCompositeDisposable: CompositeDisposable

    private var wikiList = arrayListOf<DrinkWikiMain>(
            DrinkWikiMain("소주", R.drawable.soju_img),
            DrinkWikiMain("맥주", R.drawable.beer_img),
            DrinkWikiMain("막걸리", R.drawable.makgulri_img),
            DrinkWikiMain("와인", R.drawable.wine_img)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wiki)

        val wikiAdapter = WikiAdapter(this, wikiList)
        wiki_recyclerview.adapter = wikiAdapter

        val layoutManager = GridLayoutManager(this,2)
        wiki_recyclerview.layoutManager = layoutManager
        wiki_recyclerview.setHasFixedSize(true)

        wikiAdapter.itemClick = object: WikiAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

//                mCompositeDisposable = CompositeDisposable()
//
//                mCompositeDisposable.add(DrinkService.instance.getDrinkList()
//                        .subscribeOn(Schedulers.computation())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .doOnNext() { res ->
//                            if (res.code() == HttpsURLConnection.HTTP_OK) {
//                                toast("아직 작업중입니다...")
//                                Log.d("@@Drink Response", "" + res.body())
//
//                            } else{ }
//                        }
//                        .subscribe())

                val intent = Intent(this@WikiActivity, WikiDetailActivity::class.java)
                intent.putExtra("drink_type", position+1)
                startActivity(intent)

            }
        }

        wikiAdapter.notifyDataSetChanged()

    }
}