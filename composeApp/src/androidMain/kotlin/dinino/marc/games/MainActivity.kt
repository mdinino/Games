package dinino.marc.games

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dinino.marc.games.app.ui.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            /* makes the items on the status bar visible with this theme */
            statusBarStyle = SystemBarStyle.light(0,0)
        )
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}