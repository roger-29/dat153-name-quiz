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
import io.roger.quiz.adapters.PersonListViewAdapter
import io.roger.quiz.R
import io.roger.quiz.databinding.FragmentOverviewListBinding
import io.roger.quiz.viewmodels.PersonListViewModel

class PersonListFragment : Fragment() {

    private var columnCount = 1

    private lateinit var binding: FragmentOverviewListBinding
    private lateinit var viewModel: PersonListViewModel

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

        val recyclerView = binding.list

        val adapter = PersonListViewAdapter()

        recyclerView.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }
        recyclerView.adapter = adapter

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

        return binding.root
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