package org.example.fp_helper

infix fun <A, B, C> ((A) -> B).then(f: (B) -> C): (A) -> C = { f(this(it)) }

infix fun <A, B, C, D> ((A, B, C) -> D).bind(a: A): (B, C) -> D = { b, c -> this(a, b, c) }
infix fun <A, B, C> ((A, B) -> C).bind(a: A): (B) -> C = { b -> this(a, b) }

