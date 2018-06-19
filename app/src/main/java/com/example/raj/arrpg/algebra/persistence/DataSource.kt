package com.example.raj.arrpg.algebra.persistence

import arrow.Kind
import arrow.core.Try
import arrow.core.right
import arrow.effects.IO
import arrow.effects.async
import arrow.effects.fix
import arrow.effects.monadError
import arrow.effects.typeclasses.Async
import arrow.typeclasses.binding
import com.example.raj.arrpg.algebra.domain.model.Person
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async

object DataSource {
    private fun <F, A, B> runInAsyncContext(
            f: () -> A,
            onError: (Throwable) -> B,
            onSuccess: (A) -> B, AC: Async<F>): Kind<F, B> {
        return AC.async { proc ->
            async(CommonPool) {
                val result = Try { f() }.fold(onError, onSuccess)
                proc(result.right())
            }
        }
    }

    fun fetchAllPersons(): IO<List<Person>> {
        val monadError = IO.monadError()
        return monadError.binding {
            val result = runInAsyncContext(
                    f = { getPersonsList() },
                    onError = { monadError.raiseError<List<Person>>(it) },
                    onSuccess = { monadError.just(it) },
                    AC = IO.async()
            ).bind()
            result.bind()
        }.fix()
    }

    private fun getPersonsList(): List<Person> {
        // throw RuntimeException()
        return listOf(
                Person("Name 1"),
                Person("Name 2"),
                Person("Name 3")
        )
    }
}