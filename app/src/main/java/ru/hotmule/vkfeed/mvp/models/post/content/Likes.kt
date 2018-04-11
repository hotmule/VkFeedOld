package ru.hotmule.vkfeed.mvp.models.post.content

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Likes(@Expose var count: Int,
            @Expose @SerializedName("can_like") var canLike: Int)