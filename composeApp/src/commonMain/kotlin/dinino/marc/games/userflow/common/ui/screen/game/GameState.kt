package dinino.marc.games.userflow.common.ui.screen.game
sealed interface GameState<out GAME_OVER_STATE_DETAILS: Any, out BOARD_STATE: Any> {
    val board: BOARD_STATE

    data class Normal<out GAME_OVER_STATE_DETAILS: Any, out BOARD_STATE: Any>(override val board: BOARD_STATE)
        : GameState<GAME_OVER_STATE_DETAILS, BOARD_STATE>

    data class Paused<out GAME_OVER_STATE_DETAILS: Any, out BOARD_STATE: Any>(override val board: BOARD_STATE)
        : GameState<GAME_OVER_STATE_DETAILS, BOARD_STATE>

    data class GameOver<out GAME_OVER_STATE_DETAILS: Any, out BOARD_STATE: Any>(
        override val board: BOARD_STATE,
        val details: GAME_OVER_STATE_DETAILS?
    ): GameState<GAME_OVER_STATE_DETAILS, BOARD_STATE>
}