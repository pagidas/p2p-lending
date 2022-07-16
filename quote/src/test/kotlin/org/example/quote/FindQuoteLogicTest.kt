package org.example.quote

class FindQuoteLogicTest: FindQuoteContract() {

    private val lenders = listOf(
        Lender("A", 0.075, 500),
        Lender("B", 0.069, 500),
        Lender("C", 0.071, 400)
    )

    override val findQuote: FindQuote = FindQuote { findQuoteLogic({ lenders }, it) }
}