package tabacowang.me.taipeizoomvp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import tabacowang.me.taipeizoomvp.di.networModule
import tabacowang.me.taipeizoomvp.di.repositoryModule

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(networModule, repositoryModule)
        }
    }
}