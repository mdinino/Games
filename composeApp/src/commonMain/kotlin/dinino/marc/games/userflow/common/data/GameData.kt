package dinino.marc.games.userflow.common.data

interface GameData<out GAME_OVER_DETAILS: Any, out BOARD_DATA: Any> {
    val playData: GamePlayData<GAME_OVER_DETAILS>
    val boardData: BOARD_DATA
}