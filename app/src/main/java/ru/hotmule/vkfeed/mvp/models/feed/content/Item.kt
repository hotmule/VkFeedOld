package ru.hotmule.vkfeed.mvp.models.feed.content

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item(@Expose @SerializedName("source_id") var sourceId: Int,
           @Expose @SerializedName("post_id") var postId: Int,
           @Expose var date: Int,
           @Expose var text: String)