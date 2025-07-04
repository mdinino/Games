package dinino.marc.games.platform

import app.cash.sqldelight.db.SqlDriver

expect class PlatformManager {
    val platformType: PlatformType
    val databaseDriver: SqlDriver
}