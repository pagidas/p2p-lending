package org.example.app

import org.example.fp_helper.bind
import org.example.quote.FindQuote
import org.example.quote.LoanProperties
import org.example.quote.console.quoteConsoleOutput
import org.example.quote.csv.fetchLendersFromCsv
import org.example.quote.csv.getResources
import org.example.quote.findQuoteLogic

fun main() {
    /**
     * Files are located in resources folder of this very module.
     */
    val example1 = getResources("/example1.csv")
    val example3 = getResources("/example3.csv")

    val findQuoteLogic = ::findQuoteLogic bind fetchLendersFromCsv(example1) bind LoanProperties(36)
    val findQuoteConsole = ::quoteConsoleOutput bind FindQuote(findQuoteLogic)

    findQuoteConsole(1000)
}

