package org.themoviedb.api.morepopularmoviesapp.ui

import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseApplication : AppCompatActivity(){
    val subscriptions = CompositeDisposable()

   fun subscribe(disposable: Disposable):Disposable{
        subscriptions.add(disposable)
       return disposable
   }

    override fun onStop() {
        super.onStop()
        subscriptions.clear()
    }
}