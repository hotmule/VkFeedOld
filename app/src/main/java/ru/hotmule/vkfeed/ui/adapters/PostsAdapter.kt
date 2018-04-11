package ru.hotmule.vkfeed.ui.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ru.hotmule.vkfeed.R
import ru.hotmule.vkfeed.mvp.models.feed.content.Group
import ru.hotmule.vkfeed.mvp.models.feed.content.Item
import ru.hotmule.vkfeed.mvp.models.feed.content.Profile
import ru.hotmule.vkfeed.mvp.models.feed.FeedResponse
import java.text.SimpleDateFormat
import java.util.*

class PostsAdapter(private var onBottomReachedListener: OnBottomReachedListener,
                   private var onPostClickListener: OnPostClickListener)
    : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    private var itemsData: MutableList<Item>
    private var profilesData: MutableList<Profile>
    private var groupsData: MutableList<Group>

    init {
        itemsData = mutableListOf()
        profilesData = mutableListOf()
        groupsData = mutableListOf()
    }

    fun setPosts(feedResponse: FeedResponse) {
        itemsData = feedResponse.items
        profilesData = feedResponse.profiles
        groupsData = feedResponse.groups
        notifyDataSetChanged()
    }

    fun setMorePosts(feedResponse: FeedResponse) {
        itemsData.addAll(feedResponse.items)
        profilesData.addAll(feedResponse.profiles)
        groupsData.addAll(feedResponse.groups)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.post, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == itemsData.size - 1)
            onBottomReachedListener.onBottomReached()

        val item = itemsData[position]

        val text = item.text
        val unixDate = item.date
        val sourceId = item.sourceId

        setPostText(text, holder)
        setPostDate(unixDate, holder)
        setPostAvatar(sourceId, holder)

        holder.setOnPostClickListener(item, onPostClickListener)
    }

    private fun setPostText(text: String, holder: ViewHolder) {
        if (text.isNotEmpty())
            setupTextParams(text, Color.BLACK, holder)
        else
            setupTextParams("Пост без текста", Color.GRAY, holder)
    }

    private fun setupTextParams(text: String, textColor: Int, holder: ViewHolder) {
        holder.postText.text = text
        holder.postText.setTextColor(textColor)
    }

    private fun setPostDate(unixDate: Int, holder: ViewHolder) {
        val date = Date(unixDate * 1000L)
        val simpleDateFormat = SimpleDateFormat("HH:mm, dd.MM.yyyy")
        val formattedDate = simpleDateFormat.format(date)
        holder.postDate.text = formattedDate
    }

    private fun setPostAvatar(sourceId: Int, holder: ViewHolder) {
        var imageLink = String()

        if (sourceId > 0)
            profilesData.filter { sourceId == it.id }
                    .forEach { imageLink = it.photoLink }
        else
            groupsData.filter { sourceId == -1 * it.id }
                    .forEach { imageLink = it.photoLink }

        Picasso.get().load(imageLink).into(holder.postAvatar)
    }

    override fun getItemCount() = itemsData.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var postText: TextView = itemView.findViewById(R.id.postTextView)
        val postDate: TextView = itemView.findViewById(R.id.postDateTextView)
        val postAvatar: ImageView = itemView.findViewById(R.id.avatarImageView)

        fun setOnPostClickListener(item: Item, listener: OnPostClickListener){
            itemView.setOnClickListener { listener.onPostClick(item) }
        }
    }

    interface OnBottomReachedListener {
        fun onBottomReached()
    }

    interface OnPostClickListener {
        fun onPostClick(item: Item)
    }
}