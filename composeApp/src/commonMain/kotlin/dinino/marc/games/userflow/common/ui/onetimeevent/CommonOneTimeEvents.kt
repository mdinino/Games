package dinino.marc.games.userflow.common.ui.onetimeevent

import dinino.marc.games.userflow.common.ui.route.SerializableUserFlowRoute

sealed interface CommonOneTimeEvents {

    interface NavigateOneTimeEvent: CommonOneTimeEvents {
        val route: SerializableUserFlowRoute
    }

    interface NotifyOneTimeEvent: CommonOneTimeEvents {
        val localizedMessage: suspend ()->String
        val action: (()->Unit)?
    }
}