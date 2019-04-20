package com.example.myasus.kotlin_2.lastmatch

import com.example.myasus.kotlin_2.MatchContract
import com.example.myasus.kotlin_2.entity.ftMatch
import com.example.myasus.kotlin_2.entity.repository.MatchRepoImpl
import com.example.myasus.kotlin_2.utils.Scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class LastMatchPresenter (
    val mView : MatchContract.View,
    val matchRepoImpl : MatchRepoImpl,
    val scheduler : SchedulerProvider
) : MatchContract.Presenter {

    val compositeDisposable = CompositeDisposable()

    override fun getFootballMatchData(leagueName: String) {
        mView.showLoading()
        compositeDisposable.add(matchRepoImpl.getLastMatch(leagueName)
            .observeOn(scheduler.ui())
            .subscribeOn(scheduler.io())
            .subscribeWith(object : ResourceSubscriber<ftMatch>(){
                override fun onComplete() {
                    mView.hideLoading()
                }

                override fun onNext(m: ftMatch) {
                    mView.displayFootballMatch(m.events)
                }

                override fun onError(e: Throwable?) {
                    mView.hideLoading()
                    mView.displayFootballMatch(Collections.emptyList())
                }
            }))
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }

}