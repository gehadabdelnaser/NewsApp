package com.gehad.news.api

import com.gehad.news.data.NewsResponse
import com.gehad.news.data.SourcesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    //sources
    @GET("sources")
    suspend fun getSources(@Query("apiKey") apiKey:String
                   ,@Query("language") language:String): SourcesResponse

    //everything
    @GET("everything")
    suspend fun getNews(@Query("apiKey") apiKey:String
                ,@Query("language") language:String
                ,@Query("sources") sources:String
                ,@Query("q") search:String?): NewsResponse
}