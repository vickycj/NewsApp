package com.vicky.apps.datapoints.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vicky.apps.datapoints.common.SchedulerProvider
import com.vicky.apps.datapoints.data.remote.Repository
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import android.text.format.DateUtils
import java.util.*
import kotlin.collections.ArrayList


class MainViewModel(private val repository: Repository,
                    private val schedulerProvider: SchedulerProvider
):ViewModel() {




    private val response: MutableLiveData<List<NewsDataList>> = MutableLiveData()

    private var data:List<NewsDataList> = ArrayList()

    fun getSubscription():MutableLiveData<List<NewsDataList>> = response

    private lateinit var compositeDisposable: CompositeDisposable



    private var ascendingVal:Boolean = false

    fun setCompositeData(compositeDisposable: CompositeDisposable) {
        this.compositeDisposable = compositeDisposable
    }


    fun getData():List<NewsDataList> {
        return data
    }

    fun getDataFromRemote() {

        compositeDisposable.add(generateApiCall()

        .subscribeBy ( onSuccess = {
           modifyTheResponse(it)
        }, onError = {
            Log.d("valuessss",it.message)
        } ))


    }

    private fun modifyTheResponse(data: List<NewsDataList>){
        this.data = data

        data.forEach {
            it.uiDateStamp = formatTheData(it.timeCreated)
        }
        response.postValue(data)

    }

    private fun formatTheData(timeCreated: Long?): String? {
        val date = Date(timeCreated!!)
        return DateUtils.getRelativeTimeSpanString(date.getTime(), Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS).toString()
    }

    fun generateApiCall():Single<List<NewsDataList>>{
        return repository.getDataFromApi()
            .compose(schedulerProvider.getSchedulersForSingle())
    }







}