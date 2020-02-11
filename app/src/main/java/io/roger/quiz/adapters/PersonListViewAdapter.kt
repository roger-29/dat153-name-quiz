package io.roger.quiz.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import io.roger.quiz.R
import io.roger.quiz.data.Person
import io.roger.quiz.utilities.ImageUtil
import kotlinx.android.synthetic.main.list_item.view.*

class PersonListViewAdapter internal constructor(
) : RecyclerView.Adapter<PersonListViewAdapter.PersonViewHolder>() {

    var persons = emptyList<Person>()

    inner class PersonViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val photoView: ImageView = view.photo
        val nameView: TextView = view.name
        lateinit var person: Person

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }

    }
//
//    inner class SwipeToDeleteCallBack(dragDirs: Int,
//                                      swipeDirs: Int
//    ) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
//        override fun onMove(
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            target: RecyclerView.ViewHolder
//        ): Boolean {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//        }
//
//    }

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