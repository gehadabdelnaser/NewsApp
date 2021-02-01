package com.gehad.news.repositries.sources

import com.gehad.news.api.ApiManger
import com.gehad.news.data.SourcesResponse
import com.gehad.news.model.Constant
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OnlineSourceBasedRetrofit : NewsSourcesRepo.OnlineDataSource {

    override fun getSources(): Single<SourcesResponse> {

        return ApiManger.getWebService()
            .getSources(Constant.apiKey,"en")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }
}