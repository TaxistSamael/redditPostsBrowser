package taxist.samail.redditposts.ui.feed

import taxist.samail.redditposts.data.api.error.ErrorObject
import taxist.samail.redditposts.data.api.response.RedditFeedResponse

interface FeedContract {
    interface View {
        fun onPostsSuccess(response: RedditFeedResponse, isFirstPage: Boolean)
        fun onPostsFailure(errorObject: ErrorObject)

        fun showProgress()
        fun hideProgress()
        fun hideRefreshingIndicator()
    }

    interface Presenter {
        fun getPosts(count: Int, after: String = "", isRefreshing: Boolean = false, isFirstPage: Boolean = true)
        fun unsubscribe()
    }
}