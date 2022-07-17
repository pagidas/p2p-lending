package org.example.quote.console

import org.example.quote.FindQuote
import org.example.quote.InvalidLoanAmount
import org.example.quote.NotEnoughAvailableLenders
import org.example.quote.Problem
import org.example.quote.Quote
import org.example.result.fold

fun quoteConsoleOutput(findQuote: FindQuote, loanAmount: Int) {
    findQuote(loanAmount)
        .fold(::quoteStdOut, ::problemStdOut)
        .apply(::println)
}

private fun quoteStdOut(quote: Quote): String {
    fun Quote.toStdOutFormat(): String =
        """
            Requested amount: ${requestedAmount.currency.symbol}${requestedAmount.value}
            Annual Interest Rate: ${annualPercentageInterestRate.value}%
            Monthly repayment: ${monthlyRepayment.currency.symbol}${monthlyRepayment.amount}
            Total repayment: ${totalRepayment.currency.symbol}${totalRepayment.amount}
        """.trimIndent()

    return quote.toStdOutFormat()
}

private fun problemStdOut(problem: Problem): String = when (problem) {
    is InvalidLoanAmount -> problem.reason
    is NotEnoughAvailableLenders -> problem.reason
}

