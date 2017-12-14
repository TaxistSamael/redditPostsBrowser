package com.windailyskins.android.data.api.error

import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import retrofit2.Response
import taxist.samail.redditposts.data.api.error.ErrorHandler
import taxist.samail.redditposts.data.api.error.ErrorObject
import taxist.samail.redditposts.utils.getString

class HttpErrorParser {

    var errorObject = ErrorObject(ErrorHandler.ErrorType.EVERYTHING_ELSE)

    @SuppressWarnings("all")
    constructor(response: Response<*>) {
        val jsonReader = JsonReader(response.errorBody()?.charStream())
        jsonReader.isLenient = true
        val jsonElement = JsonParser().parse(jsonReader)

        if (!jsonElement.isJsonObject) return

        val jsonObject = jsonElement.asJsonObject

        errorObject.message = jsonObject.getString("error")
        errorObject.value = response.code()
    }
}