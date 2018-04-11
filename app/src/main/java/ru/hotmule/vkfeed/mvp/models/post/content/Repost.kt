package ru.hotmule.vkfeed.mvp.models.post.content

import com.google.gson.annotations.Expose

class Repost(@Expose var text: String,
             @Expose var attachments: MutableList<Attachment>)