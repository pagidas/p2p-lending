package org.example.quote

import org.example.result.Failure
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

abstract class FindQuoteContract {

    abstract val findQuote: FindQuote

    @Test
    fun `can not find quote when loan amount is not within 1000 an 15000 inclusive`() {
        (800..999).forEach {
            val result = findQuote(it)
            assertEquals(Failure(InvalidLoanAmount()), result)
        }
        (15001..15100).forEach {
            val result = findQuote(it)
            assertEquals(Failure(InvalidLoanAmount()), result)
        }
    }

    @Test
    fun `can not find quote when loan amount is not multiple of 100`() {
        val notMultipleOf100: (Int) -> Boolean = { x -> x % 100 != 0 }

        (1000..2000).filter(notMultipleOf100).forEach {
            val result = findQuote(it)
            assertEquals(Failure(InvalidLoanAmount()), result)
        }
    }

}

