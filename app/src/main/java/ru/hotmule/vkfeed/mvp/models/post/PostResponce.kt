package ru.hotmule.vkfeed.mvp.models.post

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.hotmule.vkfeed.mvp.models.post.content.Attachment
import ru.hotmule.vkfeed.mvp.models.post.content.Likes
import ru.hotmule.vkfeed.mvp.models.post.content.Repost

class PostResponce(@Expose @SerializedName("copy_history") var reposts: MutableList<Repost>,
                   @Expose var attachments: MutableList<Attachment>,
                   @Expose var likes: Likes)