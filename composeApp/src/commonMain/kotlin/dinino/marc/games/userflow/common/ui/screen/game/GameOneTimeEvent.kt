package dinino.marc.games.userflow.common.ui.screen.game

sealed interface GameOneTimeEvent<out GAME_OVER_DETAILS: Any> {
    data class NavigateToGameOverScreen<out GAME_OVER_DETAILS: Any>(
        val gameOverDetails: GAME_OVER_DETAILS? = null
    ): GameOneTimeEvent<GAME_OVER_DETAILS>
}