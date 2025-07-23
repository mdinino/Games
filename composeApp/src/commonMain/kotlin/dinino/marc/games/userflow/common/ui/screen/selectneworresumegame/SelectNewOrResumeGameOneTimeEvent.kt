package dinino.marc.games.userflow.common.ui.screen.selectneworresumegame

sealed interface SelectNewOrResumeGameOneTimeEvent {
    data object NewGameSelected : SelectNewOrResumeGameOneTimeEvent
    data object ResumeGameSelected : SelectNewOrResumeGameOneTimeEvent
}