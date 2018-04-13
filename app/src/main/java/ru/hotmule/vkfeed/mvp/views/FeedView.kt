package ru.hotmule.vkfeed.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.hotmule.vkfeed.mvp.models.feed.content.Item
import ru.hotmule.vkfeed.mvp.models.feed.FeedResponse

@StateStrategyType(AddToEndSingleStrategy::class)
interface FeedView : MvpView {
    fun setPosts(response: FeedResponse)

    @StateStrategyType(SkipStrategy::class)
    fun setMorePosts(feedResponse: FeedResponse)

    @StateStrategyType(SkipStrategy::class)
    fun showError()

    fun showProgress()

    fun hideProgress()

    @StateStrategyType(SkipStrategy::class)
    fun openPost(item: Item)
}