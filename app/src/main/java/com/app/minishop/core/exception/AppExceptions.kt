package com.app.minishop.core.exception

import java.io.IOException

/**
 * Network Infrastructure Tier Exceptions
 */
sealed class ApiException(message: String) : IOException(message) {
    data class BadRequestException(val errorContent: String?) : ApiException("Bad Request (400)")
    data class UnauthorizedException(val errorContent: String?) : ApiException("Session Expired (401)")
    data class ForbiddenException(val errorContent: String?) : ApiException("Access Denied (403)")
    data class NotFoundException(val errorContent: String?) : ApiException("Resource Not Found (404)")
    data class ServerErrorException(val errorContent: String?) : ApiException("Internal Server Error (500)")
    data class UnknownNetworkException(val code: Int, val errorContent: String?) : ApiException("HTTP Error $code")
}

/**
 * Storage / Local Caching Tier Exceptions
 */
sealed class DatabaseException(message: String) : Exception(message) {
    object EmptyCacheException : DatabaseException("No matching records found in local database.")
    data class DiskFullException(override val message: String) : DatabaseException(message)
    data class ConstraintException(override val message: String) : DatabaseException(message)
}