package com.vicky.apps.datapoints.ui.view
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import androidx.recyclerview.widget.RecyclerView
import com.vicky.apps.datapoints.base.BaseActivity
import com.vicky.apps.datapoints.common.ViewModelProviderFactory
import com.vicky.apps.datapoints.ui.adapter.DataAdapter

import com.vicky.apps.datapoints.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

import android.view.MenuItem

import androidx.recyclerview.widget.LinearLayoutManager

import com.vicky.apps.datapoints.ui.viewmodel.NewsDataList
import com.vicky.apps.datapoints.R




class MainActivity : BaseActivity() {



    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel:MainViewModel

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeValues()
        inilializingRecyclerView()
        viewModel.getDataFromRemote()
    }

    private fun inilializingRecyclerView() {

        recyclerView = recycler_view

        recyclerView.layoutManager = LinearLayoutManager(this)


        adapter = DataAdapter(viewModel.getData())

        recyclerView.adapter = adapter
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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.recent -> {
                viewModel.sortToRecents()
                updateData()
            }

            R.id.popular -> {
                viewModel.sortToPopular()
                updateData()
            }
        }
        return true
    }

    private fun successCallback(data:List<NewsDataList>){
        updateData()
    }

    private fun updateData(){
        adapter.updateData(viewModel.getData())
    }


    private fun failureCallback(){
        Toast.makeText(this,"API failed",Toast.LENGTH_LONG).show()
    }






}
