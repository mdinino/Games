package dinino.marc.games.ui

data class UiError(val localizedMessage: String): Exception(localizedMessage)