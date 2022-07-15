/**
 * JSON and JSONB support for github.com/JetBrains/Exposed.
 *
 * Tested with
 * - github.com/impossibl/pgjdbc-ng
 * - github.com/pgjdbc/pgjdbc 42.2.x
 * - github.com/mysql/mysql-connector-j
 * - Oracle JDBC
 * - github.com/h2database/h2database
 *
 * Based on gist.github.com/qoomon/70bbbedc134fd2a149f1f2450667dc9d
 * Thanks for everyone in github.com/JetBrains/Exposed#127
 * Released on https://gist.github.com/xtexChooser/5ead6ff7f7b419c57efaa59cf2fef492
 *
 * !!MODIFIED FOR ARGONER!!
 */
package argoner.server.util.db

import argoner.server.ArgonerServer
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Function

open class JsonColumnType<T : Any>(
    val json: Json = ArgonerServer.serializer,
    val serializer: KSerializer<T>,
    val type: Type = ArgonerServer.config.database.jsonStorageType
) : StringColumnType() {

    override fun sqlType(): String = type.name

    override fun valueFromDB(value: Any) =
        json.decodeFromString(serializer, super.valueFromDB(value).toString())

    @Suppress("UNCHECKED_CAST")
    override fun notNullValueToDB(value: Any) =
        super.nonNullValueToString(json.encodeToString(serializer, value as T))

    @Suppress("UNCHECKED_CAST")
    override fun nonNullValueToString(value: Any)=
        super.nonNullValueToString(json.encodeToString(serializer, value as T))

    override fun valueToString(value: Any?): String = when (value) {
        is Iterable<*> -> notNullValueToDB(value)
        else -> super.valueToString(value)
    }

    enum class Type {

        JSON, JSONB

    }

}

inline fun <reified T : Any> Table.json(name: String, json: Json = ArgonerServer.serializer): Column<T> =
    @OptIn(InternalSerializationApi::class)
    json(name, T::class.serializer(), json)

fun <T : Any> Table.json(name: String, serializer: KSerializer<T>, json: Json = ArgonerServer.serializer) =
    registerColumn<T>(name, JsonColumnType(json, serializer))

class JsonValue<T>(
    val expr: Expression<*>,
    override val columnType: ColumnType,
    val jsonPath: List<String>
) : Function<T>(columnType) {

    override fun toQueryBuilder(queryBuilder: QueryBuilder) = queryBuilder {
        val json = columnType is JsonColumnType<*>
        if (json) append("(")
        append(expr)
        append(" #>")
        if (json) append(">")
        append(" '{${jsonPath.joinToString { escapeFieldName(it) }}}'")
        if (json) append(")::${columnType.sqlType()}")
    }

    private fun escapeFieldName(value: String) = value
        .map {
            when (it) {
                '\"' -> "\\\""
                '\r' -> "\\r"
                '\n' -> "\\n"
                else -> it
            }
        }.joinToString("").let { "\"$it\"" }

}

inline fun <reified T : Any> Column<*>.json(vararg jsonPath: String): JsonValue<T> {
    val columnType = when (T::class) {
        Boolean::class -> BooleanColumnType()
        Byte::class -> ByteColumnType()
        Short::class -> ShortColumnType()
        Int::class -> IntegerColumnType()
        Long::class -> LongColumnType()
        Float::class -> FloatColumnType()
        Double::class -> DoubleColumnType()
        String::class -> TextColumnType()
        else -> @OptIn(InternalSerializationApi::class)
        JsonColumnType(ArgonerServer.serializer, T::class.serializer())
    }
    return JsonValue(this, columnType, jsonPath.toList())
}

class JsonContainsOp(expr1: Expression<*>, expr2: Expression<*>) : ComparisonOp(expr1, expr2, "??")

infix fun <T> JsonValue<Any>.contains(t: T): JsonContainsOp =
    JsonContainsOp(this, SqlExpressionBuilder.run { wrap(t) })

infix fun <T> JsonValue<Any>.contains(other: Expression<T>): JsonContainsOp =
    JsonContainsOp(this, other)
