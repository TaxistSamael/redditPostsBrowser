package taxist.samail.redditposts.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import taxist.samail.redditposts.data.api.response.RedditFeedResponse


interface ApiInterface {
    @GET("top/.json")
    fun getTopPosts(@Query("count") count: Int, @Query("after") after: String) : Single<RedditFeedResponse>
}