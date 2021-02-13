package com.gehad.news.repositries.sources

import com.gehad.news.api.ApiManger
import com.gehad.news.data.SourcesItem
import com.gehad.news.data.SourcesResponse
import com.gehad.news.model.Constant
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OnlineSourceBasedRetrofit : NewsSourcesRepo.OnlineDataSource {

    override suspend fun getSources():List<SourcesItem> {

        val response = ApiManger.getWebService()
            .getSources(Constant.apiKey,"en")
        return response.sources.orEmpty()

    }
}