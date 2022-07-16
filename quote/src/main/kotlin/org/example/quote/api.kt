package org.example.quote

import org.example.result.Result
import java.util.Currency
import java.util.Locale

/**
 * The driving application interface.
 */
fun interface FindQuote: (Int) -> Result<Quote, Problem>

data class Quote(
    val requestedAmount: Amount,
    val annualPercentageInterestRate: AnnualPercentageInterestRate,
    val monthlyRepayment: Repayment,
    val totalRepayment: Repayment
)

sealed interface Problem
data class InvalidLoanAmount(
    val reason: String = "Loan amount must be within £1000 and £15000 and multiple of £100."
) : Problem {
    val status: String = "Bad Request"
    val code: Int = 400
}
data class NotEnoughAvailableLenders(
    val reason: String = "It is not possible to provide a quote."
): Problem {
    val status: String = "Bad Request"
    val code: Int = 400
}

data class Repayment(
    val currency: Currency = Currency.getInstance(Locale.UK),
    val amount: Double
)
data class Amount(
    val currency: Currency = Currency.getInstance(Locale.UK),
    val value: Int
)
data class AnnualPercentageInterestRate(val value: Double)

data class LoanProperties(val numberOfRepayments: Int)

/**
 * The driven application interface.
 */
fun interface FetchLenders: () -> Lenders

typealias Lenders = Collection<Lender>
data class Lender(val name: String, val annualInterestRate: Double, val available: Int)


