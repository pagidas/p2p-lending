package org.example.quote.csv

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.example.quote.FetchLenders
import org.example.quote.Lender
import org.example.result.Result
import org.example.result.fold
import java.io.File

fun fetchLendersFromCsv(file: File): FetchLenders {
    fun toLender(csvRow: Map<String, String>): Lender =
        Lender(
            csvRow.getValue("Lender"),
            csvRow.getValue("Rate").toDouble(),
            csvRow.getValue("Available").toInt()
        )

    return FetchLenders {
        Result.catching {
            csvReader().open(file) {
                readAllWithHeaderAsSequence().map(::toLender).toList()
            }
        }.fold({ it }, { emptyList() })
    }
}

