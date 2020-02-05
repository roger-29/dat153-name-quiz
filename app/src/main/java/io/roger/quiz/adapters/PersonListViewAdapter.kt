package io.roger.quiz.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.roger.quiz.R
import io.roger.quiz.data.Person
import kotlinx.android.synthetic.main.list_item.view.*

class PersonListViewAdapter internal constructor(
) : RecyclerView.Adapter<PersonListViewAdapter.PersonViewHolder>() {

    // private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var persons = emptyList<Person>()

    inner class PersonViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val photoView: ImageView = view.photo
        val nameView: TextView = view.name

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = persons[position]

        // Get Android resource ID
        val context: Context = holder.photoView.context
        val imageId: Int = context.resources
            .getIdentifier(person.photo, "drawable", context.packageName)

        Log.e(imageId.toString(), imageId.toString())

        holder.photoView.setImageResource(imageId)
        holder.nameView.text = person.name
    }

    internal fun setPersons(persons: List<Person>) {
        this.persons = persons
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = persons.size
}