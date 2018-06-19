package com.example.raj.arrpg.runtime.ui

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import arrow.effects.fix
import com.example.raj.arrpg.R
import com.example.raj.arrpg.algebra.ui.PersonsListView
import com.example.raj.arrpg.algebra.ui.Presentation
import com.example.raj.arrpg.algebra.ui.model.PersonViewModel
import com.example.raj.arrpg.runtime.ui.adapter.PersonsAdapter

class MainActivity : AppCompatActivity(), PersonsListView {
    private lateinit var root: ConstraintLayout

    private lateinit var personsList: RecyclerView

    private lateinit var adapter: PersonsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(this).inflate(R.layout.activity_main, null, false)
        setContentView(view)
        root = view.findViewById(R.id.root)
        setupRecyclerView(view)
    }

    private fun setupRecyclerView(view: View) {
        personsList = view.findViewById(R.id.personsList)
        personsList.setHasFixedSize(true)
        personsList.layoutManager = LinearLayoutManager(this)
        adapter = PersonsAdapter()
        personsList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        Presentation.showPersons(this).fix().unsafeRunAsync {}
    }

    override fun showNotFoundError() {
        Snackbar.make(root, R.string.not_found, Snackbar.LENGTH_LONG).show()
    }

    override fun showGenericError() {
        Snackbar.make(root, R.string.something_went_wrong, Snackbar.LENGTH_LONG).show()
    }

    override fun showAuthenticationError() {
        Snackbar.make(root, R.string.not_authorized, Snackbar.LENGTH_LONG).show()
    }

    override fun showPersons(heroes: List<PersonViewModel>) {
        adapter.persons = heroes
        adapter.notifyDataSetChanged()
    }


}
