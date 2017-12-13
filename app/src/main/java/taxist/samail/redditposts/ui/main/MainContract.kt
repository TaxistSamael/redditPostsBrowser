package taxist.samail.redditposts.ui.main

import taxist.samail.redditposts.model.Post


interface MainContract {
    interface View {
        fun onPostsSuccess(posts: ArrayList<Post>)
        fun onPostsFailure(localizedMessage: String)
    }

    interface Presenter {
        fun getPosts(count: Int, before: String)
        fun unsubscribe()
    }
}