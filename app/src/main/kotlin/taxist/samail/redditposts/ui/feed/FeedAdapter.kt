package taxist.samail.redditposts.ui.feed

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_post.view.*
import org.jetbrains.anko.onClick
import taxist.samail.redditposts.R
import taxist.samail.redditposts.data.api.response.RedditFeedResponse
import taxist.samail.redditposts.model.Post
import taxist.samail.redditposts.utils.getTimeLeft
import taxist.samail.redditposts.utils.inflate
import taxist.samail.redditposts.utils.setProgressiveImageUri

class FeedAdapter(val clickPostCallback: ClickPostCallback, val paginationListener: PaginationListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val OFFSET = 15
    private var dataSet = ArrayList<Post>(50)
    private var after = ""

    fun swapData(response: RedditFeedResponse, isFirstPage: Boolean) {
        after = response.after
        updateDataSet(response.feed, isFirstPage)
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PostViewHolder(parent.inflate(R.layout.list_item_post))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val vh = holder as PostViewHolder
        val post = dataSet[position]
        vh.bind(post)

        requestPagination(position)
    }

    private fun updateDataSet(posts: ArrayList<Post>, isFirstPage: Boolean) {
        if (isFirstPage) dataSet.clear()
        dataSet.addAll(posts)
    }

    private fun requestPagination(position: Int) {
        if (position == dataSet.size - OFFSET)
            paginationListener.requestNextPage(after)
    }

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(post: Post) = with(itemView) {
            onClick { clickPostCallback.onPostClicked(post) }
            item_post_author.text = post.author
            item_post_rating.text = context.getString(R.string.format_rating, post.rating)
            item_post_comments.text = context.getString(R.string.format_rating, post.comments)
            item_post_iv_thumbnail.setProgressiveImageUri(post.thumbnail)
            item_post_title.text = post.title
            item_post_date.text = context.getTimeLeft(post.date)
        }
    }

    interface ClickPostCallback {
        fun onPostClicked(post: Post)
    }

    interface PaginationListener {
        fun requestNextPage(index: String)
    }
}