package dinino.marc.games.platform


import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dinino.marc.games.Database
import java.util.Properties

actual class PlatformManager {
    actual val platformType: PlatformType = PlatformType.Desktop
    actual val databaseDriver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:database.db", Properties(), Database.Schema)
}