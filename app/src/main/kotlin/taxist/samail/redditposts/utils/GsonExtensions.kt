package taxist.samail.redditposts.utils

import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.JsonObject

fun JsonObject?.getDouble(key: String): Double? {
    return if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) null else this.get(key).asDouble
}

fun JsonObject?.getString(key: String): String {
    return if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) "" else this.get(key).asString
}

fun JsonObject?.getStringOrNull(key: String): String? {
    return if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) null else this.get(key).asString
}

fun JsonObject?.getJsonObject(key: String): JsonObject? {
    return if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) null else this.get(key).asJsonObject
}

fun JsonObject?.getJsonArray(key: String) : JsonArray? {
    return if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) null else this.get(key).asJsonArray
}

fun JsonObject?.getInt(key: String): Int? {
    return if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) null else this.get(key).asInt
}

fun JsonObject?.getLong(key: String): Long? {
    return if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) null else this.get(key).asLong
}

fun JsonObject?.getBoolean(key: String): Boolean {
    return if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) false else this.get(key).asBoolean
}
