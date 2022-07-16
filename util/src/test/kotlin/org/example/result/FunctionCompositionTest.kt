package org.example.result

import org.example.fp_helper.then
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FunctionCompositionTest {

    @Test
    fun `can compose functions returning another function`() {
        val addOne: (Int) -> Int = { x -> x + 1 }
        val divideByTwo: (Int) -> Int = { x -> x / 2 }
        fun mapToString(x: Int): String = x.toString()

        val addOneThenDivideByTwoThenToString: (Int) -> String = addOne then divideByTwo then ::mapToString

        assertEquals("1", addOneThenDivideByTwoThenToString(1))
    }

}