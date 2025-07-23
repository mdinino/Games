package dinino.marc.games.userflow.tetris.ui.screen.gameover

import dinino.marc.games.serialization.DefaultConverterJsonString
import dinino.marc.games.userflow.common.ui.route.NavigateUpNavGraphRouteEvent
import dinino.marc.games.userflow.common.ui.screen.gameover.GameOverOneTimeEvent
import dinino.marc.games.userflow.common.ui.screen.gameover.GameOverViewModel
import dinino.marc.games.userflow.selectgame.ui.SelectGameNavGraphRoute

class TetrisGameOverViewModel(
    state: TetrisGameOverState = TetrisGameOverState()
): GameOverViewModel<TetrisGameOverState, GameOverOneTimeEvent>(
        initialState = state,
        navigateToNewGameEventFactory = { NewGame },
        navigateToDifferentGameEventFactory = { DifferentGame }
)

private data object NewGame : GameOverOneTimeEvent.Navigate {
    override val routeEvent
        get() = TODO("Not yet implemented")
}

private data object DifferentGame : GameOverOneTimeEvent.Navigate {
    override val routeEvent
        get() = NavigateUpNavGraphRouteEvent(
            route = SelectGameNavGraphRoute,
            routeJsonConverter = DefaultConverterJsonString<SelectGameNavGraphRoute>(
                serializer = SelectGameNavGraphRoute::serializer()
            )
        )
}
