package dinino.marc.games.userflow.tictactoe.ui.screen.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import dinino.marc.games.app.ui.theme.sizes.sizes
import dinino.marc.games.userflow.common.ui.layout.MenuSelected
import dinino.marc.games.userflow.common.ui.screen.game.GameScreen
import dinino.marc.games.userflow.tictactoe.data.TicTacToeGameData
import dinino.marc.games.userflow.tictactoe.ui.screen.gameover.TicTacToeGameOverRoute
import games.composeapp.generated.resources.Res
import games.composeapp.generated.resources.game_over
import games.composeapp.generated.resources.player_o_wins
import games.composeapp.generated.resources.player_x_wins
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.getString

@Composable
fun TicTacToeGameScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    menuSelectedOneTimeEvent: Flow<MenuSelected>,
    vm: TicTacToeGameViewModel
) {
    GameScreen(
        modifier = modifier,
        navHostController = navHostController,
        vm = vm,
        menuSelectedOneTimeEvent = menuSelectedOneTimeEvent,
        localizedGameOverMessage = {
            getString( resource =
                when(it) {
                    is TicTacToeGameData.GameOverDetails.OWins -> Res.string.player_o_wins
                    is TicTacToeGameData.GameOverDetails.XWins -> Res.string.player_x_wins
                    null -> Res.string.game_over
                }
            )
        },
        gameOverRoute = { TicTacToeGameOverRoute }
    ) { _, board ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TicTacToeGrid()
        }
    }
}

@Composable
fun TicTacToeGrid(
    modifier: Modifier = Modifier
        .aspectRatio(ratio = 1f, matchHeightConstraintsFirst = true)
        .fillMaxHeight(),
    rowCount: UInt = 3u,
    columnCount: UInt = 3u,
    imageVector: (row: UInt, column: UInt)-> ImageVector? = { _, _, -> Icons.Default.Close },
    onClick: (row: UInt, column: UInt) -> Unit = { _, _, -> },
    cellContent: TicTacToeCellContent = {
        row, column, showTopBorder, showStartBorder, showBottomBorder, showEndBorder ->
        TicTacToeCell(
            row = row,
            column = column,
            imageVector = imageVector,
            onClick = onClick,
            showTopBorder = showTopBorder,
            showStartBorder = showStartBorder,
            showBottomBorder = showBottomBorder,
            showEndBorder = showEndBorder
        )
    }
) = LazyVerticalGrid(
    columns = GridCells.Fixed(count = columnCount.toInt()),
    modifier = modifier,
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
    row: UInt,
    column: UInt,
    imageVector: (row: UInt, column: UInt)-> ImageVector?,
    onClick: (row: UInt, column: UInt) -> Unit,
    showTopBorder: Boolean,
    showStartBorder: Boolean,
    showBottomBorder: Boolean,
    showEndBorder: Boolean,
) = TicTacToeCell(
    imageVector = imageVector(row, column),
    onClick = { onClick(row, column) },
    showTopBorder = showTopBorder,
    showStartBorder = showStartBorder,
    showBottomBorder = showBottomBorder,
    showEndBorder = showEndBorder
)

@Composable
private fun TicTacToeCell(
    imageVector: ImageVector?,
    onClick: () -> Unit,
    showTopBorder: Boolean,
    showStartBorder: Boolean,
    showBottomBorder: Boolean,
    showEndBorder: Boolean,
) {
    BoxWithDifferentBorders(
        showTopBorder = showTopBorder,
        showBottomBorder = showBottomBorder,
        showStartBorder = showStartBorder,
        showEndBorder = showEndBorder
    ) {
        IconButton (
            onClick = onClick
        ){
            imageVector?.let {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = it,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun BoxWithDifferentBorders(
    modifier: Modifier = Modifier
        .aspectRatio(ratio = 1f, matchHeightConstraintsFirst = true).
        fillMaxSize(),
    borderStroke: BorderStroke = BorderStroke(
        width = MaterialTheme.sizes.ticTacToeCellBorder.default,
        brush = SolidColor(value = MaterialTheme.colorScheme.outline)
    ),
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
private fun BorderStroke.showOrHide(show: Boolean) =
    when(show) {
        true -> this
        else -> toTransparent()
    }
@Composable
private fun BorderStroke.toTransparent() =
    copy(brush = SolidColor(value = Transparent))

@Composable
private fun BoxWithDifferentBorders(
    modifier: Modifier = Modifier,
    topBorder: BorderStroke,
    bottomBorder: BorderStroke,
    startBorder: BorderStroke,
    endBorder: BorderStroke,
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
    ) {
        content()
    }
}
