package org.example.quote

class FindQuoteLogicTest: FindQuoteContract() {
    override val findQuote: FindQuote = FindQuote { findQuoteLogic(it) }
}