package org.example.quote

import java.util.Currency
import java.util.Locale
import org.example.result.Result

// api
typealias Lenders = Collection<Lender>
data class Lender(val name: String, val annualInterestRate: Double, val available: Int)

data class Amount(
    val currency: Currency = Currency.getInstance(Locale.UK),
    val value: Int
)
data class Repayment(
    val currency: Currency = Currency.getInstance(Locale.UK),
    val amount: Double
)
data class AnnualPercentageInterestRate(val value: Double)
data class Quote(
    val requestedAmount: Amount,
    val annualPercentageInterestRate: AnnualPercentageInterestRate,
    val monthlyRepayment: Repayment,
    val totalRepayment: Repayment
)

fun interface FindQuote: (Int) -> Result<Quote, Problem>

sealed interface Problem
class InvalidLoanAmount: Problem {
    val reason: String = "Loan amount must be within £1000 and £15000 and multiple of £100."
    val status: String = "Bad Request"
    val code: Int = 400

    override fun toString(): String = "InvalidLoanAmount[reason=$reason,status=$status,code=$code]"
}
class NotEnoughAvailableLenders: Problem {
    val reason: String = "It is not possible to provide a quote."
    val status: String = "Bad Request"
    val code: Int = 400

    override fun toString(): String = "NotEnoughAvailableLenders[reason=$reason,status=$status,code=$code]"
}

// internal
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

