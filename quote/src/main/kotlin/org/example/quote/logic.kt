package org.example.quote

import org.example.result.Failure
import org.example.result.Result
import org.example.result.Success

fun findQuoteLogic(givenLoanAmount: Int): Result<Quote, Problem> {
    val loanAmount = LoanAmount.of(givenLoanAmount) ?: return Failure(InvalidLoanAmount())

    return Success(Quote(
        Amount(value = loanAmount.value),
        AnnualPercentageInterestRate(0.0),
        Repayment(amount = 0.0),
        Repayment(amount = 0.0)
    ))
}

internal class LoanAmount private constructor(val value: Int) {
    companion object {
        private fun Int.isMultipleOf(n: Int): Boolean = this % n == 0
        private fun Int.isInBoundaries(a: Int, b: Int): Boolean = this in a..b

        fun of(amountInMajorUnits: Int): LoanAmount? =
            if (amountInMajorUnits.isMultipleOf(100).and(amountInMajorUnits.isInBoundaries(1000, 15000)))
                LoanAmount(amountInMajorUnits)
            else null
    }
}

