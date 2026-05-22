package com.app.minishop.core.exception

sealed class AppException(override val message: String, val statusCode: Int? = null) : Exception(message) {

    // HTTP errors
    class BadRequestException(message: String = "Bad request") : AppException(message, 400)
    class UnauthorizedException(message: String = "Unauthorized") : AppException(message, 401)
    class ForbiddenException(message: String = "Forbidden") : AppException(message, 403)
    class NotFoundException(message: String = "Not found") : AppException(message, 404)
    class ConflictException(message: String = "Conflict") : AppException(message, 409)
    class UnprocessableException(message: String = "Unprocessable entity") : AppException(message, 422)
    class ServerException(message: String = "Server error") : AppException(message, 500)
    class ServiceUnavailableException(message: String = "Service unavailable") : AppException(message, 503)
    class GenericException(message: String, statusCode: Int? = null) : AppException(message, statusCode)

    // Network errors
    object Network : AppException("No internet connection. Please check your settings.")
    object Timeout : AppException("The server is taking too long to respond.")
    object Unknown : AppException("An unexpected error occurred.")

    // Validation errors
    class EmptyFieldException(field: String) : AppException("$field is required")
    class InvalidEmailException : AppException("Invalid email address")
    class InvalidPasswordException : AppException("Password must be at least 8 characters")
    class ValidationException(message: String) : AppException(message)
}
