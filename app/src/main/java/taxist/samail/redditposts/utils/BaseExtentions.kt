package taxist.samail.redditposts.utils

import android.app.Activity
import android.content.Context
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
