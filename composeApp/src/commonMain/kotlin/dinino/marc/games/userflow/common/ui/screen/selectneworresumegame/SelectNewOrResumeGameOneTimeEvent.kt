package dinino.marc.games.userflow.common.ui.screen.selectneworresumegame

import dinino.marc.games.userflow.common.ui.route.UserFlowRouteEvent

sealed interface SelectNewOrResumeGameOneTimeEvent {
    interface Navigate: SelectNewOrResumeGameOneTimeEvent {
        val routeEvent: UserFlowRouteEvent
    }
}
