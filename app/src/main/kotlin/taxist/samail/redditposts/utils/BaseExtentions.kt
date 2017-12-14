package taxist.samail.redditposts.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequestBuilder
import org.jetbrains.anko.find
import taxist.samail.redditposts.R
import taxist.samail.redditposts.RedditPostsApplication

val Context.app: RedditPostsApplication
    get() = applicationContext as RedditPostsApplication

fun Activity.showSnackbar(message: Int) = showIntSnackBar(this, find(android.R.id.content), message)

fun Activity.showSnackbar(message: String) = showStringSnackBar(this, find(android.R.id.content), message)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

private fun showIntSnackBar(context: Context, parent: View, message: Int) {
    val color = ContextCompat.getColor(context, android.R.color.white)
    val snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG)
    snackbar.view.setBackgroundColor(ContextCompat.getColor(context, taxist.samail.redditposts.R.color.colorPrimary))
    val textView = snackbar.view.find<TextView>(android.support.design.R.id.snackbar_text)
    textView.setTextColor(color)
    snackbar.show()
}

private fun showStringSnackBar(context: Context, parent: View, message: String) {
    val color = ContextCompat.getColor(context, android.R.color.white)
    val snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG)
    snackbar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
    val textView = snackbar.view.find<TextView>(android.support.design.R.id.snackbar_text)
    textView.setTextColor(color)
    snackbar.show()
}

fun SimpleDraweeView.setProgressiveImageUri(url: String) {
    val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
            .setProgressiveRenderingEnabled(true)
            .build()

    val newController = Fresco.newDraweeControllerBuilder()
            .setImageRequest(request)
            .setOldController(controller)
            .build()
    controller = newController
}

fun Context.getTimeLeft(date: Long): String {
    val deltaTime = System.currentTimeMillis() - date * 1000

    if (deltaTime < DateUtils.DAY_IN_MILLIS) {
        val hours = (deltaTime / 1000 / 60 / 60).toInt()
        val minutes = (deltaTime / 1000 / 60).toInt() - hours * 60
        return getString(R.string.format_time_left, hours.toString(), minutes.toString())
    }

    var diffDays = (deltaTime / (24 * 60 * 60 * 1000)).toInt()
    diffDays++
    val daysLeft = resources.getQuantityString(R.plurals.days_plurals, diffDays, diffDays)
    return getString(R.string.format_days_left, daysLeft)
}
