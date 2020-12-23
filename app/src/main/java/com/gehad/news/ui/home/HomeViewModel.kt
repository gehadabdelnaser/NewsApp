package com.gehad.news.ui.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gehad.news.api.ApiManger
import com.gehad.news.data.ArticlesItem
import com.gehad.news.data.NewsResponse
import com.gehad.news.data.SourcesItem
import com.gehad.news.data.SourcesResponse
import com.gehad.news.model.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel :ViewModel(){

    var sourcesLiveData=MutableLiveData<List<SourcesItem?>>()
    var newsLiveData=MutableLiveData<List<ArticlesItem?>?>()
    var showLoadingLiveData=MutableLiveData<Boolean>()
    var showMassageLiveData=MutableLiveData<String>()

     fun getNewsSources() {
        ApiManger.getWebService().getSources(Constant.apiKey,"en")
            .enqueue(object : Callback<SourcesResponse> {
                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    showLoadingLiveData.value=false
                    showMassageLiveData.value=t.localizedMessage
                }

                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    showLoadingLiveData.value=false
                    sourcesLiveData.value=response.body()?.sources
                }
            })
    }

     fun getNewsBySourceId(sourceId:String,word:String?){
        ApiManger.getWebService().getNews(Constant.apiKey ,"en",sourceId ,word)
            .enqueue(object :Callback<NewsResponse>{
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {

                    showMassageLiveData.value=t.localizedMessage
                }

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    newsLiveData.value=response.body()?.articles

                }
            })
    }

}