package dinino.marc.games.userflow.selectgame.ui

sealed interface SideEffect {
    data class Error(val localizedMessage: String): SideEffect
}