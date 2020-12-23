package com.gehad.news.api

import com.gehad.news.data.NewsResponse
import com.gehad.news.data.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    //sources
    @GET("sources")
    fun getSources(@Query("apiKey") apiKey:String
                   ,@Query("language") language:String): Call<SourcesResponse>

    //everything
    @GET("everything")
    fun getNews(@Query("apiKey") apiKey:String
                ,@Query("language") language:String
                ,@Query("sources") sources:String
                ,@Query("q") search:String?): Call<NewsResponse>
}