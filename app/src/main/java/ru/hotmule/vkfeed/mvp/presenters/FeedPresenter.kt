package ru.hotmule.vkfeed.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import ru.hotmule.vkfeed.app.VkApp
import ru.hotmule.vkfeed.mvp.global.BasePresenter
import ru.hotmule.vkfeed.mvp.global.VkService
import ru.hotmule.vkfeed.mvp.models.feed.Feed
import ru.hotmule.vkfeed.mvp.models.feed.content.Item
import ru.hotmule.vkfeed.mvp.models.feed.FeedResponse
import ru.hotmule.vkfeed.mvp.views.FeedView
import ru.hotmule.vkfeed.utils.ClassicSchedulers
import rx.Observable
import javax.inject.Inject

@InjectViewState
class FeedPresenter : BasePresenter<FeedView>() {

    @Inject
    lateinit var vkService: VkService

    init {
        VkApp.appComonent.inject(this)
    }

    private lateinit var token: String
    private lateinit var secondPageInfo: String
    private var isFirstPage = true

    fun onTokenLoaded(token: String) {
        this.token = token
        onFirstPageRequested()
    }

    private fun onFirstPageRequested() {
        val observable = vkService.getPosts(token)
        loadPosts(observable)
    }

    fun onBottomReached() {
        val observable = vkService.getMorePosts(token, secondPageInfo)
        loadPosts(observable)
    }

    private fun loadPosts(observable: Observable<Feed>) {
        viewState.showProgress()

        val subscription = observable
                .compose(ClassicSchedulers.apply())
                .subscribe({ answer -> onLoadingSuccess(answer.response) }, { onLoadingFailed() })
        unSubscribeOnDestroy(subscription)
    }

    private fun onLoadingSuccess(feedResponse: FeedResponse) {
        viewState.hideProgress()
        secondPageInfo = feedResponse.nextFrom

        if (isFirstPage) {
            viewState.setPosts(feedResponse)
            isFirstPage = false
        } else
            viewState.setMorePosts(feedResponse)
    }

    private fun onLoadingFailed() {
        viewState.hideProgress()
        viewState.showError()
    }

    fun onRefreshButtonClicked() {
        if (isFirstPage)
            onFirstPageRequested()
        else
            onBottomReached()
    }

    fun onPostClick(item: Item) {
        viewState.openPost(item)
    }
}