package com.gehad.news.repositries.sources


import com.gehad.news.NetworkAwareHandler
import com.gehad.news.data.SourcesItem
import com.gehad.news.data.SourcesResponse


class NewsSourcesRepo(
    private val offlineDataSource: OfflineDataSource,
    private val onlineDataSource: OnlineDataSource,
    private val networkHandler:NetworkAwareHandler) {


    suspend fun getNewsSources():List<SourcesItem> {

        return if (networkHandler.isOnline()) {
            val result = onlineDataSource.getSources()
            offlineDataSource.cacheData(result)
            result
        } else {
            offlineDataSource.getSources()
        }
    }

    interface OnlineDataSource {
        suspend fun getSources():List<SourcesItem>
    }

    interface OfflineDataSource {
        suspend fun getSources():List<SourcesItem>
        fun cacheData(data: List<SourcesItem>)
    }
}