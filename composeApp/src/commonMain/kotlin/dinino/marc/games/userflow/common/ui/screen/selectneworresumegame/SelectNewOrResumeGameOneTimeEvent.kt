package dinino.marc.games.userflow.common.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute

sealed interface SelectNewOrResumeGameOneTimeEvent {
    interface Navigate: SelectNewOrResumeGameOneTimeEvent {
        val route: SerializableUserFlowRoute
    }
}
