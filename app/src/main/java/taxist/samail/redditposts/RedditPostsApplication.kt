package taxist.samail.redditposts

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import taxist.samail.redditposts.data.api.ApiHelper


class RedditPostsApplication : Application() {
    val apiHelper by lazy { ApiHelper(this) }

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}