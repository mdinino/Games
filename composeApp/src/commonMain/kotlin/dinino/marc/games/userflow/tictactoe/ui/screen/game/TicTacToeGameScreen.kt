package dinino.marc.games.userflow.tictactoe.ui.screen.game

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dinino.marc.games.app.ui.theme.sizes.sizes
import dinino.marc.games.userflow.common.ui.layout.MenuSelected
import dinino.marc.games.userflow.common.ui.screen.game.GameScreen
import dinino.marc.games.userflow.common.ui.screen.game.GameState
import dinino.marc.games.userflow.tictactoe.ui.imageVectors.LetterO
import dinino.marc.games.userflow.tictactoe.ui.imageVectors.LetterX
import dinino.marc.games.userflow.tictactoe.ui.screen.gameover.TicTacToeGameOverRoute
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.game_over
import games.composeapp.generated.resources.userflow_tictactoe_draw
import games.composeapp.generated.resources.userflow_tictactoe_player_o_wins
import games.composeapp.generated.resources.userflow_tictactoe_player_x_wins
import games.composeapp.generated.resources.userflow_tictactoe_turn
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import dinino.marc.games.userflow.tictactoe.data.TicTacToeCell.Companion.rowRange
import dinino.marc.games.userflow.tictactoe.data.TicTacToeCell.Companion.columnRange
import dinino.marc.games.userflow.tictactoe.data.TicTacToeCell.Companion.to
import games.composeapp.generated.resources.userflow_tictactoe_player_o_normal_content_description
import games.composeapp.generated.resources.userflow_tictactoe_player_o_winner_content_description
import games.composeapp.generated.resources.userflow_tictactoe_player_x_normal_content_description
import games.composeapp.generated.resources.userflow_tictactoe_player_x_winner_content_description
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun TicTacToeGameScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    menuSelectedOneTimeEvent: Flow<MenuSelected>,
    vm: TicTacToeGameViewModel
) = GameScreen(
        modifier = modifier,
        navHostController = navHostController,
        vm = vm,
        menuSelectedOneTimeEvent = menuSelectedOneTimeEvent,
        localizedGameOverMessage = {
            getString( resource =
                when(it) {
                    TicTacToeGameOverState.Draw ->
                        Res.string.userflow_tictactoe_draw
                    TicTacToeGameOverState.PlayerXWon ->
                        Res.string.userflow_tictactoe_player_x_wins
                    TicTacToeGameOverState.PlayerOWon ->
                        Res.string.userflow_tictactoe_player_o_wins
                    null ->
                        Res.string.game_over
                }
            )
        },
        gameOverRoute = { TicTacToeGameOverRoute }
    ) { _, board ->
        TicTacToeAdaptiveContent(viewModel = vm)
    }

@Composable
private fun TicTacToeAdaptiveContent(
    viewModel: TicTacToeGameViewModel
) = BoxWithConstraints {
    val state by viewModel.gameState.collectAsStateWithLifecycle()
    val onClick: (row: Int, column: Int) -> Unit = { row, column -> viewModel.play(row, column) }

    when(maxHeight > maxWidth) {
        true -> TicTacToeColumn(gameState = state, onClick = onClick)
        else -> TicTacToeRow(gameState = state, onClick = onClick)
    }
}

@Composable
private fun TicTacToeRow(
    modifier: Modifier = Modifier.fillMaxSize(),
    gameState: TicTacToeGameState,
    onClick: (row: Int, column: Int) -> Unit
) = Row (
    modifier = modifier,
    horizontalArrangement = Arrangement.SpaceEvenly,
    verticalAlignment = Alignment.CenterVertically
) {
    Grid(gameState = gameState, onClick = onClick)
    Turn(gameState = gameState)
}

@Composable
private fun TicTacToeColumn(
    modifier: Modifier = Modifier.fillMaxSize(),
    gameState: TicTacToeGameState,
    onClick: (row: Int, column: Int) -> Unit
) = Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Grid(gameState = gameState, onClick = onClick)
        Turn(gameState = gameState)
    }

@Composable
private fun Grid(
    gameState: TicTacToeGameState,
    onClick: (row: Int, column: Int) -> Unit,
) = Grid(
    entry = { row, column ->
        gameState.board.grid[row to column].toTicTacToeCellEntry(
            forceDisabled = gameState.shouldForceDiable
        )
    },
    onClick = { row, column -> onClick(row, column) }
)

private val TicTacToeGameState.shouldForceDiable
    get() = when(this) {
        is GameState.GameOver -> true
        is GameState.Paused<*, *> -> true
        is GameState.Normal -> false
    }

@Composable
private fun Grid(
    modifier: Modifier = Modifier.aspectRatio(1f),
    rowCount: Int = rowRange.count(),
    columnCount: Int = columnRange.count(),
    entry: @Composable (row: Int, column: Int)-> TicTacToeCellEntry,
    onClick: (row: Int, column: Int) -> Unit,
    cellContent: TicTacToeCellContent = {
        row, column, showTopBorder, showStartBorder, showBottomBorder, showEndBorder ->
        TicTacToeCell(
            row = row,
            column = column,
            entry = entry,
            onClick = onClick,
            showTopBorder = showTopBorder,
            showStartBorder = showStartBorder,
            showBottomBorder = showBottomBorder,
            showEndBorder = showEndBorder
        )
    }
) = LazyVerticalGrid(
    modifier = modifier,
    columns = GridCells.Fixed(count = columnCount),
    verticalArrangement = Arrangement.Center,
    horizontalArrangement = Arrangement.Center,
) {
    for (row in rowRange) {
        for (column in columnRange) {
            item {
                cellContent(
                    row,
                    column,
                    row > 0, /* showTopBorder */
                    column > 0,  /* showStartBorder */
                    row < rowCount - 1,  /* showBottomBorder */
                    column < columnCount - 1 /* showEndBorder */
                )
            }
        }
    }
}

private typealias TicTacToeCellContent =
        @Composable (
            row: Int,
            column: Int,
            showTopBorder: Boolean,
            showStartBorder: Boolean,
            showBottomBorder: Boolean,
            showEndBorder: Boolean
        ) -> Unit

@Composable
private fun TicTacToeCell(
    modifier: Modifier = Modifier.aspectRatio(ratio = 1f),
    row: Int,
    column: Int,
    entry: @Composable (row: Int, column: Int)-> TicTacToeCellEntry,
    onClick: (row: Int, column: Int) -> Unit,
    showTopBorder: Boolean,
    showStartBorder: Boolean,
    showBottomBorder: Boolean,
    showEndBorder: Boolean,
) = TicTacToeCell(
    modifier = modifier,
    ticTacToeCellEntry = entry(row, column),
    onClick = { onClick(row, column) },
    showTopBorder = showTopBorder,
    showStartBorder = showStartBorder,
    showBottomBorder = showBottomBorder,
    showEndBorder = showEndBorder
)


@Composable
private fun TicTacToeCell(
    modifier: Modifier = Modifier,
    ticTacToeCellEntry: TicTacToeCellEntry,
    onClick: () -> Unit = {},
    showTopBorder: Boolean = true,
    showStartBorder: Boolean = true,
    showBottomBorder: Boolean = true,
    showEndBorder: Boolean = true
) {
    BoxWithDifferentBorders(
        modifier = modifier,
        showTopBorder = showTopBorder,
        showBottomBorder = showBottomBorder,
        showStartBorder = showStartBorder,
        showEndBorder = showEndBorder
    ) {
        IconButton (
            modifier = Modifier
                .padding(MaterialTheme.sizes.paddings.extraSmall)
                .fillMaxSize(),
            enabled = ticTacToeCellEntry.enabled,
            onClick = onClick,
        ) {
            ticTacToeCellEntry.playerIcon?.Content(
                modifier = modifier.fillMaxSize()
            )
        }
    }
}
@Composable
fun BoxWithDifferentBorders(
    modifier: Modifier = Modifier,
    borderStroke: BorderStroke = defaultCellBorderStroke,
    showTopBorder: Boolean = true,
    showBottomBorder: Boolean = true,
    showStartBorder: Boolean = true,
    showEndBorder: Boolean = true,
    content: @Composable () -> Unit
) = BoxWithDifferentBorders(
        modifier = modifier,
        topBorder = borderStroke.showOrHide(showTopBorder),
        bottomBorder = borderStroke.showOrHide(showBottomBorder),
        startBorder = borderStroke.showOrHide(showStartBorder),
        endBorder = borderStroke.showOrHide(showEndBorder),
        content = content
)

@Composable
private fun BoxWithDifferentBorders(
    modifier: Modifier = Modifier,
    topBorder: BorderStroke = defaultCellBorderStroke,
    bottomBorder: BorderStroke = defaultCellBorderStroke,
    startBorder: BorderStroke = defaultCellBorderStroke,
    endBorder: BorderStroke = defaultCellBorderStroke,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = contentAlignment,
        modifier = modifier.drawBehind {
            val topBorderWidthPx = topBorder.width.toPx()
            val bottomBorderWidthPx = bottomBorder.width.toPx()
            val startBorderWidthPx = startBorder.width.toPx()
            val endBorderWidthPx = endBorder.width.toPx()

            if (topBorderWidthPx  > 0f) {
                drawLine(
                    brush = topBorder.brush,
                    start = Offset(0f, topBorderWidthPx  / 2), // Draw in the middle of the stroke
                    end = Offset(size.width, topBorderWidthPx  / 2),
                    strokeWidth = topBorderWidthPx
                )
            }

            if (bottomBorderWidthPx > 0f) {
                drawLine(
                    brush = bottomBorder.brush,
                    start = Offset(0f, size.height - bottomBorderWidthPx / 2),
                    end = Offset(size.width, size.height - bottomBorderWidthPx / 2),
                    strokeWidth = bottomBorderWidthPx
                )
            }

            if (startBorderWidthPx > 0f) {
                drawLine(
                    brush = startBorder.brush,
                    start = Offset(startBorderWidthPx / 2, 0f),
                    end = Offset(startBorderWidthPx / 2, size.height),
                    strokeWidth = startBorderWidthPx
                )
            }

            if (endBorderWidthPx > 0f) {
                drawLine(
                    brush = endBorder.brush,
                    start = Offset(size.width - endBorderWidthPx / 2, 0f),
                    end = Offset(size.width -  endBorderWidthPx / 2, size.height),
                    strokeWidth =  endBorderWidthPx
                )
            }
        }
    ) { content() }
}

@Composable
private fun BorderStroke.showOrHide(show: Boolean) =
    when(show) {
        true -> this
        else -> toTransparent()
    }
@Composable
private fun BorderStroke.toTransparent() =
    copy(brush = SolidColor(value = Transparent))


@get:Composable
private val defaultCellBorderStroke
    get() = BorderStroke(
        width = MaterialTheme.sizes.ticTacToeCellBorder.default,
        brush = SolidColor(value = MaterialTheme.colorScheme.outline)
    )

@Composable
private fun Turn(
    modifier: Modifier = Modifier.wrapContentSize(),
    gameState: TicTacToeGameState
) = Turn(
    modifier = modifier,
    turnState = gameState.board.turn
)

@Composable
private fun Turn(
    modifier: Modifier = Modifier.wrapContentSize(),
    turnState: Entry.Normal
) = Turn(
    modifier = modifier,
    playerIcon = turnState.toPlayerIcon()
)

@Composable
private fun Turn(
    modifier: Modifier = Modifier.wrapContentSize(),
    playerIcon: PlayerIcon
) = Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
) {
    Text(stringResource(Res.string.userflow_tictactoe_turn))
    Spacer(Modifier.width(MaterialTheme.sizes.spacings.tiny))
    playerIcon.Content(
        modifier = Modifier
            .width(MaterialTheme.sizes.icons.default.width)
            .height(MaterialTheme.sizes.icons.default.height)
    )
}



@Immutable
private data class TicTacToeCellEntry(
    val playerIcon: PlayerIcon?,
    val enabled : Boolean
)

@Composable
private fun Entry?.toTicTacToeCellEntry(forceDisabled : Boolean) =
    when(this) {
        null -> TicTacToeCellEntry(
            playerIcon = null,
            enabled = !forceDisabled
        )
        else -> TicTacToeCellEntry(
            playerIcon = toPlayerIcon(),
            enabled = false
        )
    }


private interface PlayerIcon {
    val vector: ImageVector
    val contentDescription: String
    val tint: Color

    @Composable
    fun Content(modifier: Modifier)

    @Immutable
    data class Standard(
        override val vector: ImageVector,
        override val contentDescription: String,
        override val tint: Color
    ) : PlayerIcon {

        @Composable
        override fun Content(modifier: Modifier) =
            Icon(
                modifier = modifier,
                imageVector = vector,
                tint = tint,
                contentDescription = contentDescription
            )
    }

    @Immutable
    data class Pulsating(
        override val vector: ImageVector,
        override val contentDescription: String,
        override val tint: Color,
        val initialScale: Float = 1.0f,
        val endScale: Float = 0.7f,
        val pulseDuration : Duration = 1.seconds,
        val easing: Easing = LinearEasing

    ) : PlayerIcon {

        @Composable
        override fun Content(modifier: Modifier) =
            Content(
                modifier = modifier,
                infiniteTransition =
                    rememberInfiniteTransition(label = "player_icon_pulsating_transition")
            )

        @Composable
        fun Content(
            modifier: Modifier,
            infiniteTransition: InfiniteTransition
        ) {
            val pulsateScale by infiniteTransition.animateFloat(
                initialValue = initialScale,
                targetValue = endScale,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = pulseDuration.inWholeMilliseconds.toInt(),
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "pulsate_scale"
            )

            Icon(
                modifier = modifier.scale(pulsateScale),
                imageVector = vector,
                tint = tint,
                contentDescription = contentDescription
            )
        }

    }
}

@Composable
private fun Entry.toPlayerIcon() =
    when(this) {
        is Entry.Normal.PlayerXNormal ->
            PlayerIcon.Standard(
                vector =
                    LetterX,
                tint =
                    MaterialTheme.colorScheme.secondary,
                contentDescription =
                    stringResource(Res.string.userflow_tictactoe_player_x_normal_content_description),
            )

        is Entry.Normal.PlayerONormal ->
            PlayerIcon.Standard(
                vector =
                    LetterO,
                tint =
                    MaterialTheme.colorScheme.secondary,
                contentDescription =
                    stringResource(Res.string.userflow_tictactoe_player_o_normal_content_description),
            )


        is Entry.Winner.PlayerXWinner ->
            PlayerIcon.Pulsating(
                vector =
                    LetterX,
                tint =
                    MaterialTheme.colorScheme.secondary,
                contentDescription =
                    stringResource(Res.string.userflow_tictactoe_player_x_winner_content_description),
            )

        is Entry.Winner.PlayerOWinner ->
            PlayerIcon.Pulsating(
                vector =
                    LetterO,
                tint =
                    MaterialTheme.colorScheme.secondary,
                contentDescription =
                    stringResource(Res.string.userflow_tictactoe_player_o_winner_content_description),
            )

    }