package com.thortigen.orgabuy.fragments.catalog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thortigen.orgabuy.R
import com.thortigen.orgabuy.databinding.FragmentCatalogBinding
import com.thortigen.orgabuy.fragments.catalog.recyclerview.CatalogAdapter
import com.thortigen.orgabuy.utils.hideKeyboard
import com.thortigen.orgabuy.viewmodels.CatalogViewModel

class CatalogFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val mCatalogViewModel: CatalogViewModel by viewModels()

    private val catalogAdapter: CatalogAdapter by lazy { CatalogAdapter() }

    private var searchString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.mCatalogViewModel = mCatalogViewModel


        val floatingActionButton =
            binding.root.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        floatingActionButton?.setOnClickListener {
            val action =
                CatalogFragmentDirections.actionCatalogFragmentToItemAddFragment(searchString)
            Navigation.findNavController(binding.root).navigate(action)
        }

        setupRecyclerView()

        mCatalogViewModel.getAllItems.observe(
            viewLifecycleOwner,
            { data ->
                mCatalogViewModel.checkForCatalogIsEmpty(data)
                catalogAdapter.setData(data)
            }
        )

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_catalog_actionbar, menu)

        val search = menu.findItem(R.id.menu_catalog_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvCatalogItems
        recyclerView.adapter = catalogAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query !== null) {
            searchInDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query !== null) {
            searchInDatabase(query)
        }

        searchString = query.toString()

        return true
    }

    private fun searchInDatabase(query: String) {
        var searchQuery = query
        searchQuery = "%$searchQuery%"

        mCatalogViewModel.searchDatabase(searchQuery).observe(
            this,
            { list ->
                list?.let { catalogAdapter.setData(it) }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}