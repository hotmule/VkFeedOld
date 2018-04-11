package ru.hotmule.vkfeed.mvp.models.like

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Like(@Expose @SerializedName("response") var response: LikeResponse)