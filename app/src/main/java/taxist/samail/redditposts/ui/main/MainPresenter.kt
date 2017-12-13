package taxist.samail.redditposts.ui.main

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import taxist.samail.redditposts.data.RedditRepository


class MainPresenter(val view: MainContract.View,
                    val repository: RedditRepository) : MainContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getPosts(count: Int, before: String) {
        compositeDisposable += repository.getRedditPosts(count, before)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({posts ->
                    view.onPostsSuccess(posts)
                }, {throwable ->
                    view.onPostsFailure(throwable.localizedMessage)
                })
    }

    override fun unsubscribe() = compositeDisposable.clear()

}