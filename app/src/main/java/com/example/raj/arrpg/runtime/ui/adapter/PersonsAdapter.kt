package  com.example.raj.arrpg.runtime.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.raj.arrpg.R
import com.example.raj.arrpg.algebra.ui.model.PersonViewModel

class PersonsAdapter(
        var persons: List<PersonViewModel> = ArrayList()
) : RecyclerView.Adapter<PersonsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(persons[pos])
    }

    override fun getItemCount() = persons.size

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(person: PersonViewModel) {
            view.findViewById<TextView>(R.id.name).setText(person.name)
        }
    }
}