package com.reach.philip.arnold

import android.app.Application
import com.reach.philip.arnold.di.appModules
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin

class MainApplication: Application() , KoinComponent {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        startKoin {
            androidContext(this@MainApplication)
            modules(appModules)
        }
    }
}