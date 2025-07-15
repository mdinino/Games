package dinino.marc.games.userflow.common.ui.screen.gameover

import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute

sealed interface GameOverOneTimeEvent {
    interface Navigate: GameOverOneTimeEvent {
        val route: SerializableUserFlowRoute
    }
}
