package ru.hotmule.vkfeed.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_feed.*
import ru.hotmule.vkfeed.R
import ru.hotmule.vkfeed.mvp.models.feed.content.Item
import ru.hotmule.vkfeed.mvp.models.feed.FeedResponse
import ru.hotmule.vkfeed.utils.TokenPreference
import ru.hotmule.vkfeed.mvp.presenters.FeedPresenter
import ru.hotmule.vkfeed.mvp.views.FeedView
import ru.hotmule.vkfeed.ui.adapters.PostsAdapter
import ru.hotmule.vkfeed.utils.ErrorSnackbar

class FeedActivity :
        MvpAppCompatActivity(),
        FeedView,
        PostsAdapter.OnPostClickListener,
        PostsAdapter.OnBottomReachedListener {

    @InjectPresenter
    lateinit var feedPresenter: FeedPresenter

    companion object {
        fun buildIntent(context: Context) = Intent(context, FeedActivity::class.java)
    }

    private lateinit var postsAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val preference = TokenPreference(this)
        val token = preference.getAccessToken()

        if (token == null) openAuthPage()
        else {
            if (savedInstanceState == null)
                feedPresenter.onTokenLoaded(token)

            setupRecyclerView()
        }
    }

    private fun openAuthPage() {
        startActivity(AuthActivity.buildIntent(this))
        finish()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        postsAdapter = PostsAdapter(this, this)

        postsRecyclerView.layoutManager = layoutManager
        postsRecyclerView.adapter = postsAdapter
    }

    override fun onPostClick(item: Item) {
        feedPresenter.onPostClick(item)
    }

    override fun onBottomReached() {
        feedPresenter.onBottomReached()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun setPosts(response: FeedResponse) {
        postsAdapter.setPosts(response)
    }

    override fun setMorePosts(feedResponse: FeedResponse) {
        postsAdapter.setMorePosts(feedResponse)
    }

    override fun showError() {
        ErrorSnackbar.show(
                this,
                feedFrameLayout,
                View.OnClickListener { feedPresenter.onRefreshButtonClicked() })
    }

    override fun openPost(item: Item) {
        startActivity(PostActivity.buildIntent(this, item))
    }
}