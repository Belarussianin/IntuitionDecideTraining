package com.intuitiondecidetraining

import android.app.Application
import com.intuitiondecidetraining.di.appModule
import com.intuitiondecidetraining.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class ThisApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@ThisApp)
            modules(appModule, viewModelModule)
        }
    }
}