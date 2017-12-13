package taxist.samail.redditposts.data

import android.content.Context
import taxist.samail.redditposts.utils.app

class RedditRepository(context: Context) {
    private val apiHelper = context.app.apiHelper

    fun getRedditPosts(count: Int, before: String) = apiHelper.getRedditFeed(count, before)
            .map { it.feed }
}