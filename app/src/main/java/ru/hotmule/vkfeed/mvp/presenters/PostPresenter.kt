package ru.hotmule.vkfeed.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import ru.hotmule.vkfeed.app.VkApp
import ru.hotmule.vkfeed.mvp.global.BasePresenter
import ru.hotmule.vkfeed.mvp.global.VkService
import ru.hotmule.vkfeed.mvp.models.like.LikeResponse
import ru.hotmule.vkfeed.mvp.models.post.PostResponce
import ru.hotmule.vkfeed.mvp.models.post.content.Attachment
import ru.hotmule.vkfeed.mvp.views.PostView
import ru.hotmule.vkfeed.utils.ClassicSchedulers
import javax.inject.Inject

@InjectViewState
class PostPresenter : BasePresenter<PostView>() {

    @Inject
    lateinit var vkService: VkService

    init {
        VkApp.appComonent.inject(this)
    }

    private lateinit var token: String
    private var sourceId: Int = 0
    private var postId: Int = 0
    private var postDetails: PostResponce? = null

    fun onPostDataReceived(token: String, postId: Int, sourceId: Int, text: String) {
        viewState.setPostText(text)

        this.token = token
        this.postId = postId
        this.sourceId = sourceId

        loadPostDetails()
    }

    private fun loadPostDetails() {
        viewState.showProgress()

        val observable = vkService.getPostDetails(token, "${sourceId}_${postId}")
        val subscription = observable
                .compose(ClassicSchedulers.apply())
                .subscribe({ answer -> onLoadingSuccess(answer.response[0]) }, { onLoadingFailed() })
        unSubscribeOnDestroy(subscription)
    }

    private fun onLoadingSuccess(response: PostResponce) {
        viewState.hideProgress()

        if (this.postDetails == null) {
            this.postDetails = response

            val postPhotos = mutableListOf<String>()
            var repostText = ""

            if (response.reposts != null) {
                val repost = response.reposts[0]

                if (repost.text != null)
                    repostText = repost.text

                findPhotosInAttachments(postPhotos, repost.attachments)
            }

            findPhotosInAttachments(postPhotos, response.attachments)

            viewState.setActivityLikesTitle(response.likes.count)
            viewState.setRepost(repostText)
            viewState.setImages(postPhotos)
        }
    }

    private fun findPhotosInAttachments(postPhotos: MutableList<String>,
                                        attachments: MutableList<Attachment>) {
        if (attachments != null)
            for (attachement in attachments)
                if (attachement.photo != null)
                    postPhotos.add(attachement.photo.photoLink)
    }

    private fun onLoadingFailed() {
        viewState.hideProgress()
        viewState.showError()
    }

    fun onRefreshButtonClicked() {
        loadPostDetails()
    }

    fun onLikeButtonClicked() {
        viewState.showProgress()

        if (postDetails != null)
            if (postDetails?.likes?.canLike == 1)
                likePost()
            else
                unlikePost()
        else
            viewState.hideProgress()
    }

    private fun likePost() {
        val observable = vkService.likePost(token, sourceId, postId)
        val subscription = observable
                .compose(ClassicSchedulers.apply())
                .subscribe({ answer -> onLikeLoadingSuccess(answer.response, 0) }, { onLoadingFailed() })
        unSubscribeOnDestroy(subscription)
    }

    private fun unlikePost() {
        val observable = vkService.unlikePost(token, sourceId, postId)
        val subscription = observable
                .compose(ClassicSchedulers.apply())
                .subscribe({ answer -> onLikeLoadingSuccess(answer.response, 1) }, { onLoadingFailed() })
        unSubscribeOnDestroy(subscription)
    }

    private fun onLikeLoadingSuccess(likeResponse: LikeResponse, isCanLike: Int) {
        this.postDetails?.likes?.canLike = isCanLike
        viewState.setActivityLikesTitle(likeResponse.likes)
        viewState.hideProgress()
    }
}