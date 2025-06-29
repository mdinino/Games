package dinino.marc.games.userflow.common.ui.screen.selectneworresumegame

sealed interface SelectNewOrResumeGameOneTimeEvent {
    interface Error: SelectNewOrResumeGameOneTimeEvent
    interface Navigate: SelectNewOrResumeGameOneTimeEvent
}