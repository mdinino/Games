package dinino.marc.games.userflow.common.ui.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * A column where the widths of all the children equals the width of the width child.
 * Inspired by https://blog.stackademic.com/android-jetpackcompose-component-sizing-layout-and-two-views-same-size-2-ways-49c6420dd503
 * This file needs some polishing. It was intended to make buttons look like on Desktop and Web.
 * It accomplishes that goal well enough.
 */
@Composable
fun AlignWidthsColumnLayout(
    modifier: Modifier = Modifier,
    contents: @Composable () -> List<@Composable ()->Unit>
) {
    val spacingPx = spacingPx()
    Layout(
        modifier = modifier,
        content = { contents().forEach { it() } },
        measurePolicy = { measurables, constraints ->
            val (width, height) = calculateSizes(measurables, constraints)
            val placeables = measurables.toPlaceables(Constraints.fixed(width, height))

            layout(
                width = width ,
                height = height * 2 + spacingPx,
                placementBlock = { placeContents(spacingPx, placeables) }
            )
        }
    )
}

private fun calculateSizes(measurables: List<Measurable>, constraints: Constraints): Pair<Int, Int> {
    val textWidths = measurables.map { it.maxIntrinsicWidth(constraints.maxHeight) }
    val maxWidth = textWidths.max()
    val height = measurables.first().minIntrinsicHeight(maxWidth)
    return Pair(maxWidth, height)
}

private fun List<Measurable>.toPlaceables(constrains: Constraints): List<Placeable> {
    val placeables = mutableListOf<Placeable>()
    this.forEach { measurable ->
        placeables.add(measurable.measure(constrains))
    }
    return placeables.toList()
}

private fun Placeable.PlacementScope.placeContents(
    spacing: Int,
    placeables: List<Placeable>
) {
    var yPosition = 0
    placeables.forEach { placeable ->
        placeable.placeRelative(x = 0, y = yPosition)
        yPosition += placeable.height + spacing
    }
}

@Composable
private fun spacingPx(): Int {
    val spacingDp = 8
    val density = LocalDensity.current
    return with(density) {spacingDp.dp.toPx().roundToInt()}
}

