package argoner.server.util.db

import argoner.server.ArgonerServer
import com.mysql.cj.xdevapi.JsonParser
import org.h2.value.ValueJson
import org.jetbrains.exposed.sql.StringColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.H2Dialect
import org.jetbrains.exposed.sql.vendors.MysqlDialect
import org.jetbrains.exposed.sql.vendors.PostgreSQLDialect
import org.jetbrains.exposed.sql.vendors.currentDialect
import org.postgresql.util.PGobject

object JsonAsStringColumnType : StringColumnType() {

    override fun sqlType(): String = ArgonerServer.config.database.jsonStorageType.name

    override fun valueFromDB(value: Any) = when (val v = super.valueFromDB(value)) {
        is String -> v
        is PGobject -> v.value!!
        is com.mysql.cj.xdevapi.JsonValue -> v.toFormattedString()
        is ValueJson -> v.string
        else -> v
    }

    override fun notNullValueToDB(value: Any): Any = when (currentDialect) {
        is PostgreSQLDialect ->
            PGobject().apply {
                type = sqlType().lowercase()
                setValue(nonNullValueToString(value))
            }

        is MysqlDialect -> JsonParser.parseDoc(nonNullValueToString(value))
        is H2Dialect -> ValueJson.get(nonNullValueToString(value))
        else -> error("Unsupported dialect: $currentDialect")
    }

    override fun nonNullValueToString(value: Any) = value as String

    override fun valueToString(value: Any?): String = when (value) {
        is Iterable<*> -> nonNullValueToString(value)
        else -> super.valueToString(value)
    }

}

fun Table.jsonAsString(name: String) = registerColumn<String>(name, JsonAsStringColumnType)
