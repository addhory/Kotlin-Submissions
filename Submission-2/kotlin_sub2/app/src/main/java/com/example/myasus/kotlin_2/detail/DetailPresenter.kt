package com.example.myasus.kotlin_2.detail

import com.example.myasus.kotlin_2.entity.Teams
import com.example.myasus.kotlin_2.entity.repository.TeamRepoImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber

class DetailPresenter(val view : DetailView.View, val teamRepoImpl: TeamRepoImpl) : DetailView.Presenter{

    val compositeDisposable = CompositeDisposable()

    override fun getTeamBadgeHome(id: String) {
        compositeDisposable.add(teamRepoImpl.getTeamDetail(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: ResourceSubscriber<Teams>(){
                override fun onComplete() {

                }

                override fun onNext(t: Teams) {
                    view.displayTeamBadgeHome(t.teams[0])
                }

                override fun onError(t: Throwable?) {

                }
            }))
    }

    override fun getTeamBadgeAway(id: String) {
        compositeDisposable.add(teamRepoImpl.getTeamDetail(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: ResourceSubscriber<Teams>(){
                override fun onComplete() {

                }

                override fun onNext(t: Teams) {
                    view.displayTeamBadgeAway(t.teams[0])
                }

                override fun onError(t: Throwable?) {

                }
            }))
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }
}