package ru.hotmule.vkfeed.app

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.hotmule.vkfeed.mvp.models.feed.Feed
import ru.hotmule.vkfeed.mvp.models.like.Like
import ru.hotmule.vkfeed.mvp.models.post.Post
import rx.Observable

interface VkApi {
    companion object {
        const val BASE_URL = "https://api.vk.com/method/"
    }

    @GET("newsfeed.get")
    fun getPosts(@Query("access_token") token: String,
                 @Query("filters") filter: String,
                 @Query("start_from") startFrom: String,
                 @Query("count") count: Int,
                 @Query("v") version: String): Observable<Feed>

    @GET("wall.getById")
    fun getPostDetails(@Query("access_token") token: String,
                       @Query("posts") ownerId_postId: String,
                       @Query("copy_history_depth") copyHistoryDepth: Int,
                       @Query("v") version: String): Observable<Post>

    @GET("likes.add")
    fun likePost(@Query("access_token") token: String,
                 @Query("type") type: String,
                 @Query("owner_id") ownerId: Int,
                 @Query("item_id") itemId: Int,
                 @Query("v") version: String): Observable<Like>

    @GET("likes.delete")
    fun unlikePost(@Query("access_token") token: String,
                   @Query("type") type: String,
                   @Query("owner_id") ownerId: Int,
                   @Query("item_id") itemId: Int,
                   @Query("v") version: String): Observable<Like>
}