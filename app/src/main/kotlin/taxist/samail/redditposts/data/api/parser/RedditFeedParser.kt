package taxist.samail.redditposts.data.api.parser

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import taxist.samail.redditposts.BuildConfig
import taxist.samail.redditposts.data.api.response.RedditFeedResponse
import taxist.samail.redditposts.model.Post
import taxist.samail.redditposts.utils.*
import java.lang.reflect.Type

class RedditFeedParser : JsonDeserializer<RedditFeedResponse> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): RedditFeedResponse {
        val jsonObject = json.asJsonObject
        val response = RedditFeedResponse()

        val dataObject = jsonObject.getJsonObject("data")
        val postArray = dataObject.getJsonArray("children")

        response.after = dataObject.getString("after")

        postArray?.map { it.asJsonObject }?.forEach {
            val itemInfo = it.getJsonObject("data")
            val post = Post().apply {
                id = itemInfo.getString("id")
                title = itemInfo.getString("title")
                author = itemInfo.getString("author")
                date = itemInfo.getLong("created_utc") ?: 0L
                thumbnail = itemInfo.getString("thumbnail")
                rating = itemInfo.getInt("score") ?: 0
                comments = itemInfo.getInt("num_comments") ?: 0
                url = BuildConfig.BASE_URL + itemInfo.getString("permalink")
            }

            response.feed.add(post)
        }

        return response
    }
}