package dinino.marc.games.userflow.common.ui.selectneworresumegame

import kotlinx.coroutines.flow.Flow

interface SelectNewOrResumeGameViewModel<
        out STATE: SelectNewOrResumeGameState,
        out ONE_TIME_EVENT: SelectNewOrResumeGameOneTimeEvent
> {
    val selectNewOrResumeGameState: Flow<STATE>
    val oneTimeEvents: Flow<ONE_TIME_EVENT>

    fun selectNewGame()
    fun selectResumeGame()
}