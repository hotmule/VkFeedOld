package ru.hotmule.vkfeed.mvp.models.feed.content

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Profile(@Expose var id: Int,
              @Expose @SerializedName("photo_100") var photoLink: String)