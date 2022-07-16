package org.example.quote

import org.example.quote.test_fixtures.FindQuoteContract

class FindQuoteLogicTest: FindQuoteContract() {

    private val lenders = listOf(
        Lender("A", 0.075, 640),
        Lender("B", 0.069, 480),
        Lender("C", 0.071, 520),
        Lender("D", 0.104, 170),
        Lender("E", 0.081, 320),
        Lender("F", 0.074, 140),
        Lender("G", 0.071, 60)
    )

    override val findQuote: FindQuote = FindQuote { findQuoteLogic({ lenders }, LoanProperties(36), it) }
}