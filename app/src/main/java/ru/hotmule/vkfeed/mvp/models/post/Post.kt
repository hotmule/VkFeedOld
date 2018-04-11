package ru.hotmule.vkfeed.mvp.models.post

import com.google.gson.annotations.Expose

class Post(@Expose val response: MutableList<PostResponce>)