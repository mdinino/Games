package dinino.marc.games.userflow.tictactoe.ui.screen.game

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
import dinino.marc.games.userflow.tictactoe.data.TicTacToeCell.Companion.to
import dinino.marc.games.userflow.tictactoe.data.TicTacToeCell.Companion.ROW_COUNT
import dinino.marc.games.userflow.tictactoe.data.TicTacToeCell.Companion.COLUMN_COUNT
import games.composeapp.generated.resources.userflow_tictactoe_player_o_normal_content_description
import games.composeapp.generated.resources.userflow_tictactoe_player_o_winner_content_description
import games.composeapp.generated.resources.userflow_tictactoe_player_x_normal_content_description
import games.composeapp.generated.resources.userflow_tictactoe_player_x_winner_content_description

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
    val onClick: (row: UInt, column: UInt) -> Unit = { row, column -> viewModel.play(row, column) }

    when(maxHeight > maxWidth) {
        true -> TicTacToeColumn(state = state.board, onClick = onClick)
        else -> TicTacToeRow(state = state.board, onClick = onClick)
    }
}

@Composable
private fun TicTacToeRow(
    modifier: Modifier = Modifier.fillMaxSize(),
    state: TicTacToeBoardState,
    onClick: (row: UInt, column: UInt) -> Unit
) = Row (
    modifier = modifier,
    horizontalArrangement = Arrangement.SpaceEvenly,
    verticalAlignment = Alignment.CenterVertically
) {
    Grid(state = state, onClick = onClick)
    Turn(state = state)
}

@Composable
private fun TicTacToeColumn(
    modifier: Modifier = Modifier.fillMaxSize(),
    state: TicTacToeBoardState,
    onClick: (row: UInt, column: UInt) -> Unit
) = Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Grid(state = state, onClick = onClick)
        Turn(state = state)
    }

@Composable
private fun Grid(
    state: TicTacToeBoardState,
    onClick: (row: UInt, column: UInt) -> Unit,
) = Grid(
    entry = { row, column -> state.grid[row to column].toTicTacToeCellEntry() },
    onClick = { row, column -> onClick(row, column) }
)

@Composable
private fun Grid(
    modifier: Modifier = Modifier.aspectRatio(1f),
    rowCount: UInt = ROW_COUNT,
    columnCount: UInt = COLUMN_COUNT,
    entry: @Composable (row: UInt, column: UInt)-> TicTacToeCellEntry,
    onClick: (row: UInt, column: UInt) -> Unit,
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
    columns = GridCells.Fixed(count = columnCount.toInt()),
    verticalArrangement = Arrangement.Center,
    horizontalArrangement = Arrangement.Center,
) {
    for (row in 0u until rowCount) {
        for (column in 0u until columnCount) {
            item {
                cellContent(
                    row,
                    column,
                    row > 0u, /* showTopBorder */
                    column > 0u,  /* showStartBorder */
                    row < rowCount - 1u,  /* showBottomBorder */
                    column < columnCount - 1u /* showEndBorder */
                )
            }
        }
    }
}

private typealias TicTacToeCellContent =
        @Composable (
            row: UInt,
            column: UInt,
            showTopBorder: Boolean,
            showStartBorder: Boolean,
            showBottomBorder: Boolean,
            showEndBorder: Boolean
        ) -> Unit

@Composable
private fun TicTacToeCell(
    modifier: Modifier = Modifier.aspectRatio(ratio = 1f),
    row: UInt,
    column: UInt,
    entry: @Composable (row: UInt, column: UInt)-> TicTacToeCellEntry,
    onClick: (row: UInt, column: UInt) -> Unit,
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
            ticTacToeCellEntry.playerIcon
                ?.let {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = it.vector,
                        tint = it.tint,
                        contentDescription = it.contentDescription
                    )
            }
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
    state: TicTacToeBoardState
) = Turn(
    modifier = modifier,
    turnState = state.turn
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
    Icon(
        modifier = Modifier
            .width(MaterialTheme.sizes.icons.default.width)
            .height(MaterialTheme.sizes.icons.default.height),
        imageVector = playerIcon.vector,
        tint = playerIcon.tint,
        contentDescription = playerIcon.contentDescription
    )
}

@Immutable
private data class TicTacToeCellEntry(
    val playerIcon: PlayerIcon?,
    val enabled : Boolean
)

@Composable
private fun Entry?.toTicTacToeCellEntry() =
    when(this) {
        null -> TicTacToeCellEntry(
            playerIcon = null,
            enabled = true
        )
        else -> TicTacToeCellEntry(
            playerIcon = toPlayerIcon(),
            enabled = false
        )
    }

@Immutable
private data class PlayerIcon(
    val vector: ImageVector,
    val tint: Color,
    val contentDescription: String
)

@Composable
private fun Entry.toPlayerIcon() =
    when(this) {
        is Entry.Normal.PlayerXNormal ->
            PlayerIcon(
                vector =
                    LetterX,
                contentDescription =
                    stringResource(Res.string.userflow_tictactoe_player_x_normal_content_description),
                tint =
                    MaterialTheme.colorScheme.secondary
            )

        is Entry.Normal.PlayerONormal ->
            PlayerIcon(
                vector =
                    LetterO,
                contentDescription =
                    stringResource(Res.string.userflow_tictactoe_player_o_normal_content_description),
                tint =
                    MaterialTheme.colorScheme.secondary
            )


        is Entry.Winner.PlayerXWinner ->
            PlayerIcon(
                vector =
                    LetterX,
                contentDescription =
                    stringResource(Res.string.userflow_tictactoe_player_x_winner_content_description),
                tint =
                    MaterialTheme.colorScheme.primary
            )

        is Entry.Winner.PlayerOWinner ->
            PlayerIcon(
                vector =
                    LetterO,
                contentDescription =
                    stringResource(Res.string.userflow_tictactoe_player_o_winner_content_description),
                tint =
                    MaterialTheme.colorScheme.primary
            )

    }