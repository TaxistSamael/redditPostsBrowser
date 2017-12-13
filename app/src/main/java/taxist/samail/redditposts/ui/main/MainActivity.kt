package taxist.samail.redditposts.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.dimen
import taxist.samail.redditposts.R
import taxist.samail.redditposts.data.RedditRepository
import taxist.samail.redditposts.model.Post
import taxist.samail.redditposts.ui.dialogs.ProcessProgressDialog
import taxist.samail.redditposts.utils.showSnackbar

//todo: pagination
//todo: click
//todo: no internet handling

class MainActivity : AppCompatActivity(), MainContract.View {

    private var presenter: MainContract.Presenter? = null
    private var progressDialog: ProcessProgressDialog? = null
    private var postsAdapter: PostsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initGlobalObjects()
        initRecyclerView()

        presenter?.getPosts(50, "")
    }

    override fun onStop() {
        super.onStop()
        progressDialog?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.unsubscribe()
    }

    override fun onPostsSuccess(posts: ArrayList<Post>) {
        postsAdapter?.swapData(posts)
    }

    override fun onPostsFailure(localizedMessage: String) {
        showSnackbar(localizedMessage)
    }

    private fun initRecyclerView() = with(posts_rv) {
        layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = postsAdapter
        setHasFixedSize(true)
        val itemPadding = dimen(R.dimen.post_list_item_padding)
        addItemDecoration(VerticalPaddingItemDecorator(itemPadding, itemPadding, true, itemPadding))
    }

    private fun initGlobalObjects() {
        presenter = MainPresenter(this, RedditRepository(this))
        progressDialog = ProcessProgressDialog(this)
        postsAdapter = PostsAdapter(clickPostCallback, paginationCallback)
    }

    private val clickPostCallback = object: PostsAdapter.ClickPostCallback {
        override fun onPostClicked(post: Post) {
//            toast("click")
            //todo
        }
    }

    private val paginationCallback = object: PostsAdapter.PaginationListener {
        override fun requestNextPage(index: String) {
//            toast("pagination")
            //todo:
        }
    }
}
