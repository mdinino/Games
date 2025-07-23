package dinino.marc.games.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json

class DefaultToJsonStringConverter<in T: Any>(
    private val serializer: KSerializer<T>,
    private val serialFormat: StringFormat = Json
): ToJsonStringConverter<T> {
    override fun convertToJson(item: T) =
        serialFormat.encodeToString(serializer = serializer, value = item)
}