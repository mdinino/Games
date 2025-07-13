package dinino.marc.games.userflow.common.ui.selectneworresumegame

data class DefaultSelectNewOrResumeGameState(
    override val isSelectNewGameAvailable: Boolean = true,
    override val isSelectResumeGameAvailable: Boolean
): SelectNewOrResumeGameState