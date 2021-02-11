package com.gehad.news.repositries.sources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.gehad.news.NetworkAwareHandler
import com.gehad.news.data.SourcesItem
import com.gehad.news.data.SourcesResponse
import io.reactivex.Single

class NewsSourcesRepo(
    private val offlineDataSource: OfflineDataSource,
    private val onlineDataSource: OnlineDataSource,
    private val networkHandler:NetworkAwareHandler) {

    val sourceList = MutableLiveData<List<SourcesItem>>()

    fun getNewsSources():MutableLiveData<List<SourcesItem>>{

        if (networkHandler.isOnline()){

            onlineDataSource.getSources()
                .subscribe({
                    sourceList.value = it.sources ?: listOf(SourcesItem())
                    if (it.sources != null)
                    offlineDataSource.cacheData(it.sources)
                },{
                    Log.e("error",it.localizedMessage)
                })

        }else{
            val data = offlineDataSource.getSources()
            Log.e("data",data.toString())
            sourceList.value=data
        }
        return sourceList
    }

    interface OnlineDataSource {
        fun getSources():Single<SourcesResponse>
    }

    interface OfflineDataSource {
        fun getSources():List<SourcesItem>
        fun cacheData(data: List<SourcesItem>)
    }
}