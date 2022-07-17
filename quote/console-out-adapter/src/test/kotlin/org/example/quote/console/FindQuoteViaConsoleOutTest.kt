package org.example.quote.console

import org.example.fp_helper.bind
import org.example.quote.Amount
import org.example.quote.AnnualPercentageInterestRate
import org.example.quote.FetchLenders
import org.example.quote.FindQuote
import org.example.quote.InvalidLoanAmount
import org.example.quote.Lender
import org.example.quote.LoanProperties
import org.example.quote.NotEnoughAvailableLenders
import org.example.quote.Problem
import org.example.quote.Quote
import org.example.quote.Repayment
import org.example.quote.findQuoteLogic
import org.example.quote.test_fixtures.FindQuoteContract
import org.example.result.Failure
import org.example.result.Result
import org.example.result.Success
import java.io.ByteArrayOutputStream
import java.io.PrintStream

private val lenders = listOf(
    Lender("A", 0.075, 640),
    Lender("B", 0.069, 480),
    Lender("C", 0.071, 520),
    Lender("D", 0.104, 170),
    Lender("E", 0.081, 320),
    Lender("F", 0.074, 140),
    Lender("G", 0.071, 60)
)
private val findQuoteLogic: FindQuote = FindQuote(
    ::findQuoteLogic bind FetchLenders { lenders } bind LoanProperties(36)
)

class FindQuoteViaConsoleOutTest: FindQuoteContract() {

    private val pipeOut = ByteArrayOutputStream()

    override val findQuote: FindQuote = FindQuote { loanAmount ->
        val console = ::quoteConsoleOutput bind findQuoteLogic

        // resets output stream so old stdout is discarded
        pipeOut.reset()

        console(loanAmount)
        mapStdOutToResult()
    }

    init {
        /**
         * Sets in-memory output stream to system's out to consume stdout/
         */
        System.setOut(PrintStream(pipeOut))
    }

    private fun mapStdOutToResult(): Result<Quote, Problem> {
        val stdOutLines = pipeOut.toString()
            .trimIndent()
            .reader()
            .readLines()

        return if (stdOutLines.size > 1)
            Success(mapQuoteFromStdOut(stdOutLines))
        else
            Failure(mapProblemFromStdOut(stdOutLines))
    }

    private fun mapQuoteFromStdOut(stdOutLines: List<String>): Quote {
        val rawQuoteStdOutFormat: Map<String, String> =
            stdOutLines.groupBy(
                { line -> line.split(":")[0] },
                { line -> line.split(":")[1] }

            ).mapValues { it.value.concatOnlyDigitsAndDecimalPoints() }

        return rawQuoteStdOutFormat.toQuote()
    }
    private fun mapProblemFromStdOut(stdOutLines: List<String>): Problem {
        val invalidLoanAmount = InvalidLoanAmount()
        val notEnoughAvailableLenders = NotEnoughAvailableLenders()
        return when (stdOutLines.first()) {
            invalidLoanAmount.reason -> InvalidLoanAmount()
            notEnoughAvailableLenders.reason -> NotEnoughAvailableLenders()
            else -> error("unmapped problem")
        }
    }
}

private fun Map<String, String>.toQuote(): Quote {
    val rawAmount = getValue("Requested amount").toInt()
    val rawInterestRate = getValue("Annual Interest Rate").toDouble()
    val rawMonthlyRepayment = getValue("Monthly repayment").toDouble()
    val rawTotalRepayment = getValue("Total repayment").toDouble()

    return Quote(
        Amount(value = rawAmount),
        AnnualPercentageInterestRate(rawInterestRate),
        Repayment(amount = rawMonthlyRepayment),
        Repayment(amount = rawTotalRepayment)
    )
}
private fun List<String>.concatOnlyDigitsAndDecimalPoints(): String =
    joinToString("") { it }.filter(Char::isDigitOrDecimalPoint)

private fun Char.isDigitOrDecimalPoint() = isDigit().or(isDecimalPoint())
private fun Char.isDecimalPoint() = this == '.'

