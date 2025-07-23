package dinino.marc.games.userflow.common.ui.screen.gameover

sealed interface GameOverOneTimeEvent {
    data object StartNewGameSelected : GameOverOneTimeEvent
    data object SelectDifferentGameSelected : GameOverOneTimeEvent
}