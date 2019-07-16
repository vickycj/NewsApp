package com.vicky.apps.datapoints.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vicky.apps.datapoints.R
import com.vicky.apps.datapoints.ui.viewmodel.NewsDataList


class DataAdapter constructor(var data:List<NewsDataList>) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_child_view,parent,false)
        return DataViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        Picasso.get().load(data[position].bannerUrl).into(holder.imageViewContent)
        holder.titleTextView.text = data[position].title
        holder.subtitleTextView.text = data[position].description
        holder.timeTextView.text = data[position].timeCreated.toString()
    }

    fun updateData(data: List<NewsDataList>){
        this.data =data
        notifyDataSetChanged()
    }
    class DataViewHolder(v:View): RecyclerView.ViewHolder(v){
        var titleTextView = v.findViewById<TextView>(R.id.time_text)
        var subtitleTextView = v.findViewById<TextView>(R.id.subtitle_text)
        var imageViewContent = v.findViewById<ImageView>(R.id.image_view)
        var timeTextView = v.findViewById<TextView>(R.id.time_text)
    }
}