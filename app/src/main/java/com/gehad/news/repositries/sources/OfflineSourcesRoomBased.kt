package com.gehad.news.repositries.sources

import android.app.Application
import android.content.Context
import android.util.Log
import com.gehad.news.data.SourcesItem
import com.gehad.news.data.offlineDatabase.MyDataBase


class OfflineSourcesRoomBased(val context: Context):NewsSourcesRepo.OfflineDataSource {


    override fun getSources(): List<SourcesItem> {
        val data = MyDataBase.getInstance(context)?.newsDoe()?.getAllNews()

        if(data !=null) return data
        else
            return listOf()
    }

    override fun cacheData(data: List<SourcesItem>) {
        MyDataBase.getInstance(context)?.newsDoe()?.deleteAll()
        for(i in data)
        MyDataBase.getInstance(context)?.newsDoe()?.insertNews(i)
    }
}