package com.gehad.news.di

import com.gehad.news.MyNetworkAwareHandler
import com.gehad.news.NetworkAwareHandler
import com.gehad.news.repositries.sources.NewsSourcesRepo
import com.gehad.news.repositries.sources.OfflineSourcesRoomBased
import com.gehad.news.repositries.sources.OnlineSourceBasedRetrofit
import com.gehad.news.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val dataSourceModule = module {
    factory<NewsSourcesRepo.OfflineDataSource> { OfflineSourcesRoomBased(androidContext())  }
    factory<NewsSourcesRepo.OnlineDataSource> { OnlineSourceBasedRetrofit() }
}

val networkModule = module {
    single<NetworkAwareHandler> { MyNetworkAwareHandler(androidContext()) }
}
val repositriesModule = module {
    single { NewsSourcesRepo(get(),get(), get()) }
}

val viewModelsModule = module {
    viewModel{ HomeViewModel(get())}
}