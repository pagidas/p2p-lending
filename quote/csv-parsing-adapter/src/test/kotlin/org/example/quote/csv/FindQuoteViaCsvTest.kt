package org.example.quote.csv

import org.example.quote.FindQuote
import org.example.quote.LoanProperties
import org.example.quote.findQuoteLogic
import org.example.quote.test_fixtures.FindQuoteContract

class FindQuoteViaCsvTest: FindQuoteContract() {

    private val csv = getResources("/test.csv")

    override val findQuote: FindQuote = FindQuote {
        findQuoteLogic(fetchLendersFromCsv(csv), LoanProperties(36), it)
    }
}