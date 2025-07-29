package dinino.marc.games.userflow.common.ui.screen.game

interface GameUiState<out BOARD_STATE: Any>{
    val boardState: BOARD_STATE

    interface Normal<out BOARD_STATE: Any>: GameUiState<BOARD_STATE>
    interface Paused<out BOARD_STATE: Any>: GameUiState<BOARD_STATE> {
        interface UserPaused<out BOARD_STATE: Any>: Paused<BOARD_STATE>
        interface UserToConfirmGameOver<out BOARD_STATE: Any>: Paused<BOARD_STATE>
    }
    interface GameOver<out BOARD_STATE: Any>: GameUiState<BOARD_STATE> {
        interface UserInitiatedGameOver<out BOARD_STATE: Any> : GameOver<BOARD_STATE>
    }
}