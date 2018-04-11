package ru.hotmule.vkfeed.mvp.global

import ru.hotmule.vkfeed.app.VkApi

class VkService(private var vkApi: VkApi) {

    private val filter = "post"
    private val count = 20
    private val version = "5.74"

    fun getPosts(token: String)
            = vkApi.getPosts(token, filter, "0", count, version)

    fun getMorePosts(token: String, startFrom: String)
            = vkApi.getPosts(token, filter, startFrom, count, version)

    fun getPostDetails(token: String, ownerId_postId: String)
            = vkApi.getPostDetails(token, ownerId_postId, 2, version)

    fun likePost(token: String, ownerId: Int, itemId: Int)
            = vkApi.likePost(token, filter, ownerId, itemId, version)

    fun unlikePost(token: String, ownerId: Int, itemId: Int)
            = vkApi.unlikePost(token, filter, ownerId, itemId, version)
}