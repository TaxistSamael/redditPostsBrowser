package taxist.samail.redditposts.ui.feed

import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_feed.*
import org.jetbrains.anko.dimen
import taxist.samail.redditposts.R
import taxist.samail.redditposts.data.RedditRepository
import taxist.samail.redditposts.data.api.error.ErrorHandler.ErrorType.EVERYTHING_ELSE
import taxist.samail.redditposts.data.api.error.ErrorHandler.ErrorType.IO
import taxist.samail.redditposts.data.api.error.ErrorObject
import taxist.samail.redditposts.data.api.response.RedditFeedResponse
import taxist.samail.redditposts.model.Post
import taxist.samail.redditposts.ui.dialogs.ProcessProgressDialog
import taxist.samail.redditposts.utils.showSnackbar

class FeedActivity : AppCompatActivity(), FeedContract.View {

    private val PAGE_SIZE = 50

    private var presenter: FeedContract.Presenter? = null
    private var progress: ProcessProgressDialog? = null
    private var feedAdapter: FeedAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        initGlobalObjects()
        initRecyclerView()
        initListeners()

        presenter?.getPosts(count = PAGE_SIZE, isFirstPage = true)
    }

    override fun onStop() {
        super.onStop()
        progress?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.unsubscribe()
    }

    override fun onPostsSuccess(response: RedditFeedResponse, isFirstPage: Boolean) {
        feedAdapter?.swapData(response, isFirstPage)

        when (feedAdapter?.itemCount == 0) {
            true -> showPlaceholder()
            false -> hidePlaceholder()
        }
    }

    override fun onPostsFailure(errorObject: ErrorObject) {
        when (errorObject.errorType) {
            IO -> showSnackbar(R.string.error_no_internet)
            EVERYTHING_ELSE -> showSnackbar(R.string.error_internal_error)
            else -> showSnackbar(errorObject.message)
        }

        if (feedAdapter?.itemCount == 0) showPlaceholder()
    }

    private fun showPlaceholder() {
        feed_tv_placeholder.visibility = VISIBLE
        feed_rv.visibility = GONE
    }

    private fun hidePlaceholder() {
        feed_tv_placeholder.visibility = GONE
        feed_rv.visibility = VISIBLE
    }

    override fun showProgress() {
        progress?.show(isCancelable = false)
    }

    override fun hideProgress() {
        progress?.hide()
    }

    override fun hideRefreshingIndicator() {
        feed_refresh.isRefreshing = false
    }

    private fun initListeners() {
        feed_refresh.setOnRefreshListener {
            presenter?.getPosts(count = PAGE_SIZE, isRefreshing = true, isFirstPage = true)
        }
    }

    private fun initRecyclerView() = with(feed_rv) {
        layoutManager = LinearLayoutManager(this@FeedActivity)
        adapter = feedAdapter
        setHasFixedSize(true)
        val itemPadding = dimen(R.dimen.post_list_item_padding)
        addItemDecoration(VerticalPaddingItemDecorator(itemPadding, itemPadding, true, itemPadding))
    }

    private fun initGlobalObjects() {
        presenter = FeedPresenter(this, RedditRepository(this))
        progress = ProcessProgressDialog(this)
        feedAdapter = FeedAdapter(clickPostCallback, paginationCallback)
    }

    private val clickPostCallback = object : FeedAdapter.ClickPostCallback {
        override fun onPostClicked(post: Post) {
            openLinkInBrowser(post.url)
        }
    }

    private val paginationCallback = object : FeedAdapter.PaginationListener {
        override fun requestNextPage(index: String) {
            presenter?.getPosts(count = PAGE_SIZE, after = index, isRefreshing = false, isFirstPage = false)
        }
    }

    private fun openLinkInBrowser(url: String) {
        try {
            openChromeTab(url)
        } catch (e: ActivityNotFoundException) {
            showSnackbar(R.string.error_no_browser)
        }
    }

    private fun openChromeTab(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true)
        builder.setToolbarColor(ContextCompat.getColor(this, android.R.color.black))
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }
}
