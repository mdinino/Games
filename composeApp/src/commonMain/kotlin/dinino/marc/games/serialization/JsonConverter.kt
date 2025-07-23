package dinino.marc.games.serialization

typealias JsonString = String

interface JsonConverter<T: Any> : ToJsonStringConverter<T>, FromJsonStringConverter<T>
interface ToJsonStringConverter<in T: Any> {
    fun convertToJson(item: T): JsonString
}

interface FromJsonStringConverter<out T: Any> {
    fun convertFromJson(jsonString: JsonString): T
}