package org.example.quote

import java.util.Currency
import java.util.Locale

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

