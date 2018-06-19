package com.example.raj.arrpg.algebra.ui

import arrow.effects.IO
import arrow.effects.fix
import arrow.effects.monadError
import arrow.typeclasses.bindingCatch
import com.example.raj.arrpg.algebra.domain.model.PersonError
import com.example.raj.arrpg.algebra.persistence.DataSource
import com.example.raj.arrpg.algebra.ui.model.PersonViewModel

interface PersonsView {
    fun showNotFoundError()

    fun showGenericError()

    fun showAuthenticationError()
}

interface PersonsListView : PersonsView {
    fun showPersons(heroes: List<PersonViewModel>)
}

object Presentation {
    fun showPersons(view: PersonsListView): IO<Unit> {
        val monadError = IO.monadError()
        return monadError.bindingCatch {
            val result = DataSource.fetchAllPersons().handleError {
                displayErrors(view, it); emptyList()
            }.bind()

            monadError.just(view.showPersons(
                    result.map {
                        PersonViewModel(it.name)
                    })
            ).bind()
        }.fix()
    }

    private fun displayErrors(view: PersonsView, t: Throwable): IO<Unit> =
            IO.monadError().just(when (PersonError.fromThrowable(t)) {
                is PersonError.NotFoundError -> view.showNotFoundError()
                is PersonError.UnknownServerError -> view.showGenericError()
                is PersonError.AuthenticationError -> view.showAuthenticationError()
            }).fix()
}