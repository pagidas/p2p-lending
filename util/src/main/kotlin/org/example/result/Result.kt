package org.example.result

/**
 * custom result type that represents success or failure
 */
sealed interface Result<out VALUE, out FAILURE> {
    val success: VALUE?
    val failure: FAILURE?
}
data class Success<out V>(val value: V): Result<V, Nothing> {
    override fun toString(): String = "Ok($value)"
    override val success: V = value
    override val failure: Nothing? = null
}
data class Failure<out E>(val error: E): Result<Nothing, E> {
    override fun toString(): String = "Err($error)"
    override val success: Nothing? = null
    override val failure: E = error
}

fun <V1, V2, E> Result<V1, E>.map(f: (V1) -> V2): Result<V2, E> = when (this) {
    is Success -> Success(f(value))
    is Failure -> this
}

fun <V, E1, E2> Result<V, E1>.mapFailure(f: (E1) -> E2): Result<V, E2> = when (this) {
    is Success -> this
    is Failure -> Failure(f(error))
}

fun <V, E, U> Result<V, E>.fold(onSuccess: (V) -> U, onFailure: (E) -> U): U = when (this) {
    is Success -> onSuccess(value)
    is Failure -> onFailure(error)
}

