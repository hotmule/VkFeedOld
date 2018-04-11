package ru.hotmule.vkfeed.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PostView : MvpView {
    fun setPostText(text: String)

    fun setRepost(repostText: String)

    fun setImages(imageLinks: MutableList<String>)

    fun showProgress()

    fun hideProgress()

    fun setActivityLikesTitle(likesCount: Int)

    @StateStrategyType(SkipStrategy::class)
    fun showError()
}