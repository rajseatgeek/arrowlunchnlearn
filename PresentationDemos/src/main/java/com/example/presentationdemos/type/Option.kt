package com.example.presentationdemos.type

// Defining Option to be a monad (and hence an applicative and a functor)

sealed class Option<out T> {
    object None : Option<Nothing>()

    data class Some<out T>(val value: T) : Option<T>()

    companion object {
        // Applicative - 1
        fun <T> pure(value: T): Option<T> = Some(value)
    }
}

// Functor
fun <T, R> Option<T>.map(f: (T) -> R): Option<R> = when (this) {
    Option.None -> Option.None
    is Option.Some -> Option.Some(f(value))
}

// Applicative - 2
fun <T, R> Option<T>.ap(f: Option<(T) -> R>): Option<R> = when {
    this is Option.Some && f is Option.Some -> Option.Some(f.value(this.value))
    else -> Option.None
}

// Monad
fun <T, R> Option<T>.flatMap(f: (T) -> Option<R>): Option<R> = when (this) {
    Option.None -> Option.None
    is Option.Some -> f(value)
}

// Using flatMap to build other ops

fun <T, R> Option<T>.mapWithFlatMap(f: (T) -> R): Option<R> = flatMap { Option.Some(f(it)) }

fun <T, R> Option<T>.apWithFlatMap(f: Option<(T) -> R>): Option<R> = flatMap { t: T -> f.map { it(t) } }
