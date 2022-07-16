package org.example.quote

import org.example.fp_helper.then
import org.example.result.Failure
import org.example.result.Result
import org.example.result.Success
import java.math.RoundingMode
import kotlin.math.pow

fun findQuoteLogic(fetchLenders: FetchLenders, loanProps: LoanProperties, givenLoanAmount: Int): Result<Quote, Problem> {
    fun Lenders.totalAvailable(): Int = map(Lender::available).fold(0, Int::plus)

    val numberOfRepayments = loanProps.numberOfRepayments
    val lenders = fetchLenders()

    val loanAmount = LoanAmount.of(givenLoanAmount) ?: return Failure(InvalidLoanAmount())
    if (loanAmount.value > lenders.totalAvailable()) return Failure(NotEnoughAvailableLenders())

    val annualInterestRate = lowestPossibleAnnualInterestRate(loanAmount.value, lenders)
    val periodicInterestRate = annualInterestRate / 12
    val periodicPayment = periodicPayment(givenLoanAmount, periodicInterestRate, numberOfRepayments)

    return Success(
        Quote(
            Amount(value = loanAmount.value),
            annualPercentageInterestRate(annualInterestRate),
            monthlyRepayment(periodicPayment),
            totalRepayment(numberOfRepayments)(periodicPayment)
        )
    )
}

private fun lowestPossibleAnnualInterestRate(loanAmount: Int, lenders: Lenders): Double {
    var acc = 0
    return lenders
        .asSequence()
        .sortedBy(Lender::annualInterestRate)
        .takeWhileInc {
            acc += it.available
            acc <= loanAmount
        }
        .map(Lender::annualInterestRate)
        .average()
}

private fun periodicPayment(principal: Int, periodicInterestRate: Double, totalNumberOfPayments: Int): Double =
    (principal * periodicInterestRate) / (1 - (1 + periodicInterestRate).pow(-totalNumberOfPayments))

private fun <T> Sequence<T>.takeWhileInc(p: (T) -> Boolean): Sequence<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = p(it)
        result
    }
}


private val percentage: (Double) -> Double = { d -> d * 100 }
private fun roundHalfUp(scale: Int): (Double) -> Double = { d ->
    d.toBigDecimal().setScale(scale, RoundingMode.HALF_UP).toDouble()
}
private fun times(numberOfPayments: Int): (Double) -> Double = { d -> d * numberOfPayments }
private val mapToRepayment: (Double) -> Repayment = { d -> Repayment(amount = d) }
private val mapToAnnualPercentageInterestRate: (Double) -> AnnualPercentageInterestRate = ::AnnualPercentageInterestRate

private val monthlyRepayment: (Double) -> Repayment = roundHalfUp(2) then mapToRepayment
private val totalRepayment: (Int) -> (Double) -> Repayment = { numberOfRepayments ->
    times(numberOfRepayments) then roundHalfUp(2) then mapToRepayment
}
private val annualPercentageInterestRate: (Double) -> AnnualPercentageInterestRate =
    percentage then roundHalfUp(1) then mapToAnnualPercentageInterestRate

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

