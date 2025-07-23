package dinino.marc.games.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json

class DefaultJsonConverter<T: Any>(
    private val serializer: KSerializer<T>,
    private val serialFormat: StringFormat = Json
): JsonConverter<T> {
    override fun convertToJson(item: T) =
        serialFormat.encodeToString(serializer, item)

    override fun convertFromJson(jsonString: JsonString) =
        Json.decodeFromString(serializer,jsonString)
}