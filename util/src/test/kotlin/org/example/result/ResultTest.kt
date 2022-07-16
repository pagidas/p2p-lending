package org.example.result

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ResultTest {

    @Test
    fun `can map a success in another result`() {
        val result: Result<*, *> = Success("a").map { "b" }

        assertEquals(Success("b"), result)
    }

    @Test
    fun `can map a failure in another result`() {
        val result: Result<*, *> = Failure(1).mapFailure { 10 }

        assertEquals(Failure(10), result)
    }

    @Test
    fun `can fold a result into another type`() {
        val resultA: String = Success(10).fold(
            { it.toString() },
            { "Not failure!" }
        )
        val resultB: String = Failure("an error").fold(
            { "Not success!" },
            { "Got error: $it" }
        )

        assertEquals("10", resultA)
        assertEquals("Got error: an error", resultB)
    }

    @Test
    fun `can catch an exception and map it to result`() {
        val result = Result.catching { error("something naughty happened") }
        val failed = result.mapFailure(Exception::message)

        assertEquals(Failure("something naughty happened"), failed)
    }

}