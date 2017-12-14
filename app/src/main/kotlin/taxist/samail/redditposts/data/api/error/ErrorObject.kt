package taxist.samail.redditposts.data.api.error

data class ErrorObject(var errorType: ErrorHandler.ErrorType,
                       var message: String = "",
                       var value: Int = 0)