package com.app.minishop.core.common

object Constants {
    // API
    const val BASE_URL = "https://fakestoreapi.com/"

    const val NETWORK_TIMEOUT = 15L
    const val API_TIMEOUT_SECONDS = 30L
    const val READ_TIMEOUT_SECONDS = 30L
    const val WRITE_TIMEOUT_SECONDS = 30L
    const val CONNECT_TIMEOUT_SECONDS = 10L

    // Headers
    const val CONTENT_TYPE = "application/json"
    const val AUTHORIZATION_HEADER = "Authorization"
    const val BEARER_PREFIX = "Bearer "

    // Database
    const val DATABASE_NAME = "minishop_db"
    const val DATABASE_VERSION = 1

    // Preferences
    const val PREFERENCES_NAME = "minishop_preferences"
    const val PREF_AUTH_TOKEN = "auth_token"
    const val PREF_REFRESH_TOKEN = "refresh_token"
    const val PREF_USER_ID = "user_id"
    const val PREF_USER_EMAIL = "user_email"
    const val PREF_IS_LOGGED_IN = "is_logged_in"
    const val PREF_FIRST_TIME_LAUNCH = "first_time_launch"
    const val PREF_APP_LANGUAGE = "app_language"
    const val PREF_DARK_MODE = "dark_mode"
    const val PREF_CURRENCY = "currency"

    // Cache
    const val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB
    const val CACHE_MAX_AGE_HOURS = 24

    // Pagination
    const val DEFAULT_PAGE_SIZE = 20
    const val DEFAULT_PAGE_NUMBER = 1

    // Validation
    const val MIN_PASSWORD_LENGTH = 8
    const val MIN_NAME_LENGTH = 2
    const val MAX_NAME_LENGTH = 100
    const val MIN_PHONE_LENGTH = 10
    const val MAX_PHONE_LENGTH = 15

    // Error Messages
    const val ERROR_NETWORK = "Network error. Please check your connection."
    const val ERROR_TIMEOUT = "Request timeout. Please try again."
    const val ERROR_UNAUTHORIZED = "Unauthorized. Please login again."
    const val ERROR_FORBIDDEN = "Access forbidden."
    const val ERROR_NOT_FOUND = "Resource not found."
    const val ERROR_SERVER = "Server error. Please try again later."
    const val ERROR_UNKNOWN = "An unknown error occurred."
    const val ERROR_VALIDATION = "Invalid input. Please check and try again."

    // Success Messages
    const val SUCCESS_LOGIN = "Login successful!"
    const val SUCCESS_REGISTER = "Registration successful!"
    const val SUCCESS_LOGOUT = "Logout successful!"
    const val SUCCESS_UPDATE = "Update successful!"
    const val SUCCESS_DELETE = "Delete successful!"
    const val SUCCESS_ADD_CART = "Product added to cart!"
    const val SUCCESS_REMOVE_CART = "Product removed from cart!"

    // Time
    const val TOKEN_EXPIRY_MINUTES = 60
    const val SESSION_TIMEOUT_MINUTES = 30

    // API Endpoints
    object Endpoints {
        const val PRODUCTS = "products"
        const val PRODUCT_DETAIL = "products/{id}"
        const val CARTS = "/carts"
        const val CART = "/carts/{id}"
        const val SEARCH = "products/search"
        const val CATEGORIES = "categories"
        const val LOGIN = "auth/login"
        const val REGISTER = "auth/register"
        const val REFRESH_TOKEN = "auth/refresh"
        const val LOGOUT = "auth/logout"
        const val PROFILE = "users/profile"
        const val ORDERS = "orders"
        const val ORDER_DETAIL = "orders/{id}"
        const val WISHLIST = "wishlist"
        const val FAVORITES = "favorites"
    }

    // HTTP Status Codes
    const val HTTP_OK = 200
    const val HTTP_CREATED = 201
    const val HTTP_NO_CONTENT = 204
    const val HTTP_BAD_REQUEST = 400
    const val HTTP_UNAUTHORIZED = 401
    const val HTTP_FORBIDDEN = 403
    const val HTTP_NOT_FOUND = 404
    const val HTTP_CONFLICT = 409
    const val HTTP_UNPROCESSABLE = 422
    const val HTTP_SERVER_ERROR = 500
    const val HTTP_SERVICE_UNAVAILABLE = 503
}