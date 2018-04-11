package ru.hotmule.vkfeed.mvp.models.feed

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.hotmule.vkfeed.mvp.models.feed.content.Group
import ru.hotmule.vkfeed.mvp.models.feed.content.Item
import ru.hotmule.vkfeed.mvp.models.feed.content.Profile

class FeedResponse(@Expose var items: MutableList<Item>,
                   @Expose var profiles: MutableList<Profile>,
                   @Expose var groups: MutableList<Group>,
                   @Expose @SerializedName("next_from") var nextFrom: String)