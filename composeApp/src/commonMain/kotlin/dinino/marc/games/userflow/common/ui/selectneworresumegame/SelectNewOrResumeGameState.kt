package dinino.marc.games.userflow.common.ui.selectneworresumegame

import dinino.marc.games.DataType

interface SelectNewOrResumeGameState: DataType {
    val isSelectNewGameAvailable: Boolean
    val isSelectResumeGameAvailable: Boolean
}