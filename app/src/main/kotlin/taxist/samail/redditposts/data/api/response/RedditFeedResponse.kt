package taxist.samail.redditposts.data.api.response

import taxist.samail.redditposts.model.Post


class RedditFeedResponse {
    var feed = ArrayList<Post>(50)
    var after = ""
}