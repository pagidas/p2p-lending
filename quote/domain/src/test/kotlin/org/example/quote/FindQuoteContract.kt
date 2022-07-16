package org.example.quote

import org.example.result.Failure
import org.example.result.Success
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

    @Test
    fun `can not provide a quote when given loan amount exceeds lenders available budgets`() {
       val result = findQuote(2400)
       assertEquals(Failure(NotEnoughAvailableLenders()), result)
    }

    @Test
    fun `can provide a quote finding the lowest possible annual interest rate according to lenders`() {
        val result = findQuote(1700)

        val quote = expectedQuote(
            1700,
            7.2,
            52.65,
            1895.28
        )

        assertEquals(Success(quote), result)
    }

    private fun expectedQuote(
        requestedAmount: Int,
        annualPercentageInterestRate: Double,
        monthlyRepayment: Double,
        totalRepayment: Double,
    ): Quote = Quote(
        Amount(value = requestedAmount),
        AnnualPercentageInterestRate(annualPercentageInterestRate),
        Repayment(amount = monthlyRepayment),
        Repayment(amount = totalRepayment)
    )

}

