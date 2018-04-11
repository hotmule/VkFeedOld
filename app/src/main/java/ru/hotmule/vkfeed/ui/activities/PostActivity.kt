package ru.hotmule.vkfeed.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post.*
import ru.hotmule.vkfeed.R
import ru.hotmule.vkfeed.mvp.models.feed.content.Item
import ru.hotmule.vkfeed.mvp.presenters.PostPresenter
import ru.hotmule.vkfeed.mvp.views.PostView
import ru.hotmule.vkfeed.utils.ErrorSnackbar
import ru.hotmule.vkfeed.utils.TokenPreference
import java.lang.Exception

class PostActivity : MvpAppCompatActivity(), PostView {

    @InjectPresenter
    lateinit var postPresenter: PostPresenter

    companion object {
        private const val POST_ID_ARG = "postId"
        private const val SOURCE_ID_ARG = "sourceId"
        private const val TEXT_ARG = "text"

        fun buildIntent(context: Context, item: Item): Intent {
            val intent = Intent(context, PostActivity::class.java)
            intent.putExtra(POST_ID_ARG, item.postId)
            intent.putExtra(SOURCE_ID_ARG, item.sourceId)
            intent.putExtra(TEXT_ARG, item.text)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        if (savedInstanceState == null) {
            val postId = intent.getIntExtra(POST_ID_ARG, 0)
            val sourceId = intent.getIntExtra(SOURCE_ID_ARG, 0)
            val text = intent.getStringExtra(TEXT_ARG)

            val tokenPreference = TokenPreference(this)
            val token = tokenPreference.getAccessToken()

            postPresenter.onPostDataReceived(token, postId, sourceId, text)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.post_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.like_button -> {
            postPresenter.onLikeButtonClicked()
            true
        }
        else -> {super.onOptionsItemSelected(item)}
    }

    override fun setPostText(text: String) {
        supportActionBar?.title = getString(R.string.action_bar_likes_title)
        setupTextView(selectedPostTextView, text)
    }

    override fun setActivityLikesTitle(likesCount: Int) {
        supportActionBar?.title = getString(R.string.action_bar_likes_title) + " " + likesCount.toString()
    }

    override fun setRepost(repostText: String) {
        setupTextView(repostTextView, repostText)
    }

    fun setupTextView(textView: TextView, text: String) {
        if (text.isNotEmpty())
            textView.text = text
        else
            textView.visibility = View.GONE
    }

    override fun setImages(imageLinks: MutableList<String>) {
        if (imageLinks.size != 0) {
            for (imageLink in imageLinks) {

                val imageView = ImageView(this)
                imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                imageView.adjustViewBounds = true

                imageProgressBar.visibility = View.VISIBLE

                Picasso.get().load(imageLink).into(imageView, object : Callback {
                    override fun onSuccess() {
                        hideImageProgress()
                    }

                    override fun onError(e: Exception?) {
                        hideImageProgress()
                    }
                })

                val linearLayoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)

                postLinearLayout.addView(imageView, linearLayoutParams)
            }
        }
    }

    private fun hideImageProgress() {
        imageProgressBar.visibility = View.GONE
    }

    override fun showProgress() {
        postProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        postProgressBar.visibility = View.GONE
    }

    override fun showError() {
        ErrorSnackbar.show(
                this,
                postLinearLayout,
                View.OnClickListener { postPresenter.onRefreshButtonClicked() })
    }
}
