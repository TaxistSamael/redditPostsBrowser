package taxist.samail.redditposts.ui.main

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils.SECOND_IN_MILLIS
import android.text.format.DateUtils.getRelativeTimeSpanString
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_post.view.*
import org.jetbrains.anko.onClick
import taxist.samail.redditposts.R
import taxist.samail.redditposts.model.Post
import taxist.samail.redditposts.utils.inflate
import java.util.*


class PostsAdapter(val clickPostCallback: ClickPostCallback, val paginationListener: PaginationListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataSet = ArrayList<Post>(50)
    private val OFFSET = 15
    private val PAGE_SIZE = 50

    fun swapData(posts: ArrayList<Post>) {
        dataSet = posts
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PostViewHolder(parent.inflate(R.layout.list_item_post))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val vh = holder as PostViewHolder
        val post = dataSet[position]
        vh.bind(post)

        requestPagination(post, position)
    }

    private fun requestPagination(post: Post, position: Int) {
        if (position != dataSet.size - OFFSET) return
        paginationListener.requestNextPage(post.id)
    }

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(post: Post) = with(itemView) {
            onClick { clickPostCallback.onPostClicked(post) }
            item_post_author.text = post.author
            item_post_rating.text = context.getString(R.string.format_rating, post.rating)
            item_post_comments.text = context.getString(R.string.format_rating, post.comments)
//            item_post_iv_thumbnail.setImageURI(post.thumbnail)
            item_post_title.text = post.title
            item_post_date.text = "${post.date}"
        }

        private fun getRelativeTime(date: Long) =
                getRelativeTimeSpanString(date, System.currentTimeMillis(), SECOND_IN_MILLIS)
    }

    interface ClickPostCallback {
        fun onPostClicked(post: Post)
    }

    interface PaginationListener {
        fun requestNextPage(index: String)
    }
}