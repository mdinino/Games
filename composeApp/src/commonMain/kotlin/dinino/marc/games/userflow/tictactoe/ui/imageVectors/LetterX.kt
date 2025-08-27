package dinino.marc.games.userflow.tictactoe.ui.imageVectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * Generated with https://github.com/ComposeGears/Valkyrie
 */
val LetterX: ImageVector
    get() {
        if (_LetterX != null) {
            return _LetterX!!
        }
        _LetterX = ImageVector.Builder(
            name = "LetterX",
            defaultWidth = 28.dp,
            defaultHeight = 28.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1.75f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(7f, 4f)
                lineToRelative(10f, 16f)
            }
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1.75f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(17f, 4f)
                lineToRelative(-10f, 16f)
            }
        }.build()

        return _LetterX!!
    }

@Suppress("ObjectPropertyName")
private var _LetterX: ImageVector? = null


