package dinino.marc.games.platform

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import dinino.marc.games.Database

actual class PlatformManager {
    actual val platformType: PlatformType = PlatformType.IOS
    actual val databaseDriver: SqlDriver = NativeSqliteDriver(Database.Schema, "test.db")
}