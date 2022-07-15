package argoner.server.config

import argoner.server.util.db.JsonColumnType
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Slf4jSqlDebugLogger

@Serializable
data class DatabaseConfig(
    val url: String,
    val driver: String? = null,
    val user: String = "",
    val password: String = "",
    val jsonStorageType: JsonColumnType.Type = JsonColumnType.Type.JSONB,
) {

    fun connect() {
        val databaseConfig = org.jetbrains.exposed.sql.DatabaseConfig {
            sqlLogger = Slf4jSqlDebugLogger
        }
        if (driver == null) {
            Database.connect(url = url, user = user, password = password, databaseConfig = databaseConfig)
        } else {
            Database.connect(
                url = url,
                driver = driver,
                user = user,
                password = password,
                databaseConfig = databaseConfig
            )
        }
    }

}