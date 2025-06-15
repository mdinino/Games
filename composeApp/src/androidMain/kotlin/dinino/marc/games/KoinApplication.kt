package dinino.marc.games

import android.app.Application
import dinino.marc.games.app.di.initKoin

class KoinApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}