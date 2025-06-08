package dinino.marc.games.ui.screen.selectgames

sealed interface SideEffect {
    data class Error(val localizedMessage: String): SideEffect
}