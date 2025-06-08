package dinino.marc.games.ui.screen.selectgames

sealed interface SideEffect {
    data class ShowSnackbar(val localizedMessage: String): SideEffect
}