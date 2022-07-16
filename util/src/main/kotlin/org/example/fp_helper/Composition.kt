package org.example.fp_helper

infix fun <A, B, C> ((A) -> B).then(f: (B) -> C): (A) -> C = { f(this(it)) }

