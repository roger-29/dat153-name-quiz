package io.roger.quiz.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import io.roger.quiz.adapters.PersonListViewAdapter
import io.roger.quiz.R
import io.roger.quiz.databinding.FragmentOverviewListBinding
import io.roger.quiz.viewmodels.PersonListViewModel

class PersonListFragment : Fragment() {

    private var columnCount = 1

    private lateinit var binding: FragmentOverviewListBinding
    private lateinit var viewModel: PersonListViewModel
    private lateinit var adapter: PersonListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOverviewListBinding.inflate(inflater, container, false)

        //val recyclerView = binding.list

        adapter = PersonListViewAdapter()
        binding.list.adapter = adapter

        binding.list.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }

        // Get a new or existing ViewModel from the ViewModelProvider.
        viewModel = ViewModelProvider(this).get(PersonListViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.allPersons.observe(viewLifecycleOwner, Observer { persons ->
            // Update the cached copy of the words in the adapter.
            persons?.let { adapter.setPersons(it) }
        })

        binding.fab.setOnClickListener {
            Log.i("PersonListFragment", "Fab pressed")
            this.findNavController()
                .navigate(PersonListFragmentDirections
                    .actionPeopleToAddPerson())
        }

        setRecyclerViewItemTouchListener()

        return binding.root
    }

    private fun setRecyclerViewItemTouchListener() {

        //1
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPos: Int = viewHolder.getAdapterPosition();
                val toPos: Int = target.getAdapterPosition();
                // move item in `fromPos` to `toPos` in adapter.
                return true;// true if moved, false otherwise
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //3
                val person = adapter.persons[viewHolder.adapterPosition]
                viewModel.deletePerson(person)
//                val position = viewHolder.adapterPosition
//                photosList.removeAt(position)
//                recyclerView.adapter!!.notifyItemRemoved(position)
            }
        }

        //4
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.list)
    }


    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            PersonListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}