package taxist.samail.redditposts.model


class Post (var id: String = "",
            var title: String = "",
            var author: String = "",
            var date: Long = 0L,
            var thumbnail: String = "",
            var rating: Int = 0,
            var comments: Int = 0)
