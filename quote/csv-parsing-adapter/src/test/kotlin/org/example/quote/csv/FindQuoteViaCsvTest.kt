package org.example.quote.csv

import org.example.quote.FindQuote
import org.example.quote.LoanProperties
import org.example.quote.findQuoteLogic
import org.example.quote.test_fixtures.FindQuoteContract
import java.io.File
import java.io.IOException

class FindQuoteViaCsvTest: FindQuoteContract() {
    private fun getResources(fileName: String): File {
        val uri = object {}.javaClass.getResource(fileName)?.toURI()
            ?: throw IOException("Cannot find test.csv file with given path $fileName")
        return File(uri)
    }

    private val csv = getResources("/test.csv")

    override val findQuote: FindQuote = FindQuote {
        findQuoteLogic(fetchLendersFromCsv(csv), LoanProperties(36), it)
    }
}