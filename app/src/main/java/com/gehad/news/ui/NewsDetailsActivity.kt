package com.gehad.news.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gehad.news.R
import com.gehad.news.data.ArticlesItem
import com.gehad.news.model.Constant
import kotlinx.android.synthetic.main.activity_news_details.*
import kotlinx.android.synthetic.main.activity_news_details.date_of_published_tv
import kotlinx.android.synthetic.main.activity_news_details.description_tv
import kotlinx.android.synthetic.main.activity_news_details.news_image
import kotlinx.android.synthetic.main.activity_news_details.news_title_tv

class NewsDetailsActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        val news=intent.extras?.getParcelable<ArticlesItem>(Constant.NEWS)

        Glide.with(this).load(news?.urlToImage).into(news_image)
        news_title_tv.text=news?.title
        date_of_published_tv.text=news?.publishedAt
        description_tv.text=news?.description
        author_tv.text="Author : ${news?.author}"
        source_tv.text="Source : ${news?.source?.name}"

    }
}
