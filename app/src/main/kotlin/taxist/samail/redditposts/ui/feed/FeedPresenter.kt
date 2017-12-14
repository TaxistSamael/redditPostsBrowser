package taxist.samail.redditposts.ui.feed

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import taxist.samail.redditposts.data.RedditRepository
import taxist.samail.redditposts.data.api.error.ErrorHandler

class FeedPresenter(val view: FeedContract.View,
                    val repository: RedditRepository) : FeedContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getPosts(count: Int, after: String, isRefreshing: Boolean, isFirstPage: Boolean) {
        if (isRefreshing.not()) view.showProgress()

        compositeDisposable += repository.getRedditPosts(count, after)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { view.hideRefreshingIndicator() }
                .subscribe({ response ->
                    view.hideProgress()
                    view.onPostsSuccess(response, isFirstPage)
                }, { throwable ->
                    view.hideProgress()
                    view.onPostsFailure(ErrorHandler.getErrorObject(throwable))
                })
    }

    override fun unsubscribe() = compositeDisposable.clear()
}