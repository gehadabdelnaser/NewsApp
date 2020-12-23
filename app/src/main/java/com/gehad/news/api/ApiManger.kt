package com.gehad.news.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManger {

    companion object{
        private var retrofit : Retrofit
        init {
            retrofit=Retrofit.Builder()
                .baseUrl("http://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun getApis():Retrofit{
            return retrofit
        }

        fun getWebService():WebServices{
            return getApis().create(WebServices::class.java)
        }
    }

}