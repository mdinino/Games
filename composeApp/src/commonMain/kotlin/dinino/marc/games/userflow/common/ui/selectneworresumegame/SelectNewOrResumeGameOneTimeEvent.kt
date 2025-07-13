package dinino.marc.games.userflow.common.ui.selectneworresumegame

import dinino.marc.games.userflow.common.ui.onetimeevent.CommonOneTimeEvents

sealed interface SelectNewOrResumeGameOneTimeEvent {
    interface  Navigate: SelectNewOrResumeGameOneTimeEvent, CommonOneTimeEvents.NavigateOneTimeEvent
}
