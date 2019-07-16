package com.vicky.apps.datapoints.ui.view
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vicky.apps.datapoints.base.BaseActivity
import com.vicky.apps.datapoints.common.ViewModelProviderFactory
import com.vicky.apps.datapoints.ui.adapter.DataAdapter

import com.vicky.apps.datapoints.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.vicky.apps.datapoints.base.AppConstants
import com.vicky.apps.datapoints.ui.viewmodel.NewsDataList


class MainActivity : BaseActivity() {



    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel:MainViewModel

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.vicky.apps.datapoints.R.layout.activity_main)
        initializeValues()
        inilializingRecyclerView()
        viewModel.getDataFromRemote()
    }

    private fun inilializingRecyclerView() {

        recyclerView.layoutManager = LinearLayoutManager(this)


        adapter = DataAdapter(getEmptyData())

        recyclerView.adapter = adapter
    }

    private fun getEmptyData(): List<NewsDataList> {
        return ArrayList()
    }

    private fun initializeValues() {

        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        viewModel.setCompositeData(compositeDisposable)

        viewModel.getSubscription().observe(this, Observer {
            if(it.isNotEmpty()){
                successCallback(it)
            }else{
                failureCallback()
            }
        })
    }


    private fun successCallback(data:List<NewsDataList>){
        updateData(data)
    }

    private fun updateData(data:List<NewsDataList>){
        adapter.updateData(data)
    }


    private fun failureCallback(){
        Toast.makeText(this,"API failed",Toast.LENGTH_LONG).show()
    }






}
