package dinino.marc.games.userflow.common.data

interface GameData<GAME_STATE: GameState, BOARD_DATA: Any> {
    val gameState: GAME_STATE
    val boardData: BOARD_DATA
}