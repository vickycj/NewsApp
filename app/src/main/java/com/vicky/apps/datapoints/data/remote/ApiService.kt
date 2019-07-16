package com.vicky.apps.datapoints.data.remote


import com.vicky.apps.datapoints.ui.viewmodel.NewsDataList
import io.reactivex.Single
import retrofit2.http.GET


interface ApiService {
    @GET("carousell-interview-assets/android/carousell_news.json")
    fun getDataFromService(): Single<List<NewsDataList>>
}
