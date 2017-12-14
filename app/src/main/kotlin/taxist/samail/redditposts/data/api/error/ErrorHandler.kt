package taxist.samail.redditposts.data.api.error

import com.windailyskins.android.data.api.error.HttpErrorParser
import io.reactivex.exceptions.CompositeException
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class ErrorHandler {

    enum class ErrorType {
        IO,
        EVERYTHING_ELSE,
        DEFAULT;

        companion object {
            fun getCheckedValue(type: String): ErrorType {
                values().forEach {
                    if (it.name.equals(type, true)) return it
                }
                return DEFAULT
            }
        }
    }

    companion object {
        fun getErrorObject(throwable: Throwable): ErrorObject {
            return when (throwable) {
                is HttpException -> getHttpErrorObject(throwable)
                is CompositeException -> getCompositeErrorObject(throwable)
                is IOException, is UnknownHostException -> return ErrorObject(ErrorType.IO)
                else -> return ErrorObject(ErrorType.EVERYTHING_ELSE)
            }
        }

        private fun getCompositeErrorObject(throwable: CompositeException): ErrorObject {
            throwable.exceptions.forEach {
                return when (it) {
                    is IOException, is UnknownHostException -> ErrorObject(ErrorType.IO)
                    else -> ErrorObject(ErrorType.EVERYTHING_ELSE)
                }
            }

            return ErrorObject(ErrorType.EVERYTHING_ELSE)
        }

        private fun getHttpErrorObject(throwable: HttpException): ErrorObject {
            val errorParser = HttpErrorParser(throwable.response())
            return errorParser.errorObject
        }
    }
}