package com.gehad.news.ui.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gehad.news.api.ApiManger
import com.gehad.news.data.ArticlesItem
import com.gehad.news.data.SourcesItem
import com.gehad.news.model.Constant
import com.gehad.news.repositries.sources.NewsSourcesRepo
import kotlinx.coroutines.launch


class HomeViewModel(private val newsSourcesRepo : NewsSourcesRepo ) : ViewModel() {

    val sourcesLiveData = MutableLiveData<List<SourcesItem>>()

    val newsLiveData=MutableLiveData<List<ArticlesItem?>?>()
    val showLoadingLiveData=MutableLiveData<Boolean>()
    val showMassageLiveData=MutableLiveData<String>()


     fun getNewsSources() {
         showLoadingLiveData.value=true
         //use coroutines scope(viewModelScope) to run suspend fun
         viewModelScope.launch {
            val result = newsSourcesRepo.getNewsSources()
             sourcesLiveData.postValue(result)
         }
    }

     fun getNewsBySourceId(sourceId: String, word: String?){
         // used coroutines
         //  Use try , catch when the connection is disconnected
         try {
             viewModelScope.launch {
                 val response = ApiManger.getWebService()
                     .getNews(Constant.apiKey, "en", sourceId, word)
                 newsLiveData.postValue(response.articles)
             }
         }catch (t : Throwable){
             showLoadingLiveData.value=false
             showMassageLiveData.value=t.localizedMessage
         }
    }
}