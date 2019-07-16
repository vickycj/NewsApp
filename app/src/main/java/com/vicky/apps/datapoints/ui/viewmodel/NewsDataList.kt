package com.vicky.apps.datapoints.ui.viewmodel


import com.google.gson.annotations.SerializedName

data class NewsDataList(
    @SerializedName("banner_url")
    var bannerUrl: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("rank")
    var rank: Int?,
    @SerializedName("time_created")
    var timeCreated: Int?,
    @SerializedName("title")
    var title: String?
)