package dinino.marc.games.platform

import app.cash.sqldelight.db.SqlDriver

actual class PlatformManager {
    actual val platformType: PlatformType = PlatformType.WasmJs
    actual val databaseDriver: SqlDriver = TODO("Not supported by SQLDelight or Room yet")
}