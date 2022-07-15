package argoner.server.util.db

import argoner.server.ArgonerServer
import org.h2.value.ValueJson
import org.jetbrains.exposed.sql.StringColumnType
import org.jetbrains.exposed.sql.Table

object JsonAsStringColumnType : StringColumnType() {

    override fun sqlType(): String = ArgonerServer.config.database.jsonStorageType.name

    override fun valueFromDB(value: Any): String = if (value is ValueJson) value.string else value.toString()

    override fun notNullValueToDB(value: Any) = super.nonNullValueToString(value as String)

    override fun nonNullValueToString(value: Any) = super.nonNullValueToString(value as String)

    override fun valueToString(value: Any?): String = when (value) {
        is Iterable<*> -> notNullValueToDB(value)
        else -> super.valueToString(value)
    }

}

fun Table.jsonAsString(name: String) = registerColumn<String>(name, JsonAsStringColumnType)
