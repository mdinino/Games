package dinino.marc.games.serialization

typealias JsonString = String

interface ConverterJsonString<T: Any> : ToJsonStringStringConverter<T>, FromJsonStringConverter<T>
interface ToJsonStringStringConverter<in T: Any> {
    fun convertToJson(item: T): JsonString
}

interface FromJsonStringConverter<out T: Any> {
    fun convertFromJson(jsonString: JsonString): T
}