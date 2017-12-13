package taxist.samail.redditposts.data.api

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import taxist.samail.redditposts.BuildConfig
import taxist.samail.redditposts.data.api.parser.RedditFeedParser
import taxist.samail.redditposts.data.api.response.RedditFeedResponse
import java.util.concurrent.TimeUnit


class ApiHelper(context: Context) {
    private var apiInterface: ApiInterface
    private var client: OkHttpClient
    private val timeout: Long = 10

    companion object {
        private const val CACHE_SIZE_BYTES: Long = 1024 * 1024 * 10 //10 MB
    }

    init {
        val gson = GsonBuilder()
                .registerTypeAdapter(RedditFeedResponse::class.java, RedditFeedParser())
                .create()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        client = OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .cache(Cache(context.cacheDir, CACHE_SIZE_BYTES))
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

        apiInterface = retrofit.create(ApiInterface::
        class.java)
    }

    fun getRedditFeed(count: Int, after: String) = apiInterface.getTopPosts(count, after)
}