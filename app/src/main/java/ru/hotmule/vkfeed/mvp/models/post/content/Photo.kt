package ru.hotmule.vkfeed.mvp.models.post.content

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Photo(@Expose @SerializedName(
        "photo_1280",
        alternate = [
            "photo_807",
            "photo_604",
            "photo_130",
            "photo_75"
        ]) var photoLink: String)