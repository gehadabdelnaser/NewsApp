package com.gehad.news.ui.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gehad.news.api.ApiManger
import com.gehad.news.data.ArticlesItem
import com.gehad.news.data.SourcesItem
import com.gehad.news.model.Constant
import com.gehad.news.repositries.sources.NewsSourcesRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class HomeViewModel(private val newsSourcesRepo : NewsSourcesRepo ) : ViewModel() {

    lateinit var sourcesLiveData : MutableLiveData<List<SourcesItem>>

    val newsLiveData=MutableLiveData<List<ArticlesItem?>?>()
    val showLoadingLiveData=MutableLiveData<Boolean>()
    val showMassageLiveData=MutableLiveData<String>()
    private val compositeDisposable=CompositeDisposable()


    init{
        sourcesLiveData=newsSourcesRepo.sourceList
        newsSourcesRepo.getNewsSources()
    }


    private val errorHandler= Consumer<Throwable>{
        showLoadingLiveData.value=false
        showMassageLiveData.value=it.localizedMessage
    }

     fun getNewsSources() {

         showLoadingLiveData.value=true
         newsSourcesRepo.getNewsSources()
    }

     fun getNewsBySourceId(sourceId: String, word: String?){
         // used RX java
        val disposable = ApiManger.getWebService()
            .getNews(Constant.apiKey, "en", sourceId, word)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                newsLiveData.value = it?.articles
            }, this.errorHandler)

         // dispose for all operation in background
         compositeDisposable.add(disposable)

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}