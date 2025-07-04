package dinino.marc.games

import android.app.Application
import dinino.marc.games.app.di.initKoin
import org.koin.android.ext.koin.androidContext

class KoinApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@KoinApplication)
        }
    }
}