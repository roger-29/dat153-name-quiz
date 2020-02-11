package io.roger.quiz.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.roger.quiz.R
import io.roger.quiz.data.Person
import io.roger.quiz.utilities.ImageUtil
import kotlinx.android.synthetic.main.list_item.view.*

class PersonListViewAdapter internal constructor(
) : RecyclerView.Adapter<PersonListViewAdapter.PersonViewHolder>() {

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

        val bitmap = ImageUtil.decodeRoomImageToBitmap(person.photo)

        Log.e("PersonListViewAdapter","${person.name} fail")

        holder.photoView.setImageBitmap(bitmap)
        holder.nameView.text = person.name

    }

    internal fun setPersons(persons: List<Person>) {
        this.persons = persons
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = persons.size
}