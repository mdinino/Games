package dinino.marc.games.platform

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dinino.marc.games.Database


actual class PlatformManager(ctx: Context) {
    actual val platformType: PlatformType = PlatformType.Android
    actual val databaseDriver: SqlDriver = AndroidSqliteDriver(Database.Schema, ctx, "database.db")
}