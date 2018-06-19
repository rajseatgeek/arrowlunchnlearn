package com.example.presentationdemos.type

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import arrow.core.Try
import arrow.core.Tuple3
import arrow.core.toT
import arrow.data.State
import arrow.data.fix
import arrow.instances.monad
import arrow.typeclasses.binding

// Either
val DIVIDE_BY_ZERO = "Divide by zero"

fun eitherDivide(num: Int, den: Int): Either<String, Float> = if (den == 0) {
    Left(DIVIDE_BY_ZERO)
} else {
    Right(num.div(den.toFloat()))
}

// Try
private fun tryDivide(num: Int, den: Int): Try<Int> = Try { num / den }

fun tryDivision(a: Int, b: Int): Try<Tuple3<Try<Int>, Int, Int>> {
    val aDiv = tryDivide(a, b)
    return when (aDiv) {
        is Try.Success -> {
            Try { Tuple3(aDiv, a, b) }
        }
        is Try.Failure -> Try.Failure(aDiv.exception)
    }
}

// State
private fun double(): State<Int, Unit> = State {
    it * 2 toT Unit
}

private fun add3(): State<Int, Unit> = State {
    it + 3 toT Unit
}

private fun finalValue(): State<Int, Int> = State {
    it toT it
}

fun doubleAndAddThree() = State().monad<Int>().binding {
    double().bind()
    add3().bind()

    finalValue().bind()
}.fix()

