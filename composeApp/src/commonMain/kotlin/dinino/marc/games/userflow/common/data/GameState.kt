package dinino.marc.games.userflow.common.data

interface GameState {
    interface Normal: GameState
    interface Paused: GameState {
        interface UserPaused: Paused
        interface UserToConfirmGameOver: Paused
    }
    interface GameOver: GameState {
        interface UserInitiatedGameOver : GameOver
    }
}
