package dinino.marc.games.userflow.common.ui.screen.game

sealed interface GameOneTimeEvent {
    data object NavigateToGameOverScreen: GameOneTimeEvent
}