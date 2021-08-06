package com.thortigen.orgabuy.fragments.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.thortigen.orgabuy.databinding.FragmentCatalogBinding
import com.thortigen.orgabuy.fragments.catalog.recyclerview.CatalogAdapter
import com.thortigen.orgabuy.viewmodels.CatalogViewModel

class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val mCatalogViewModel: CatalogViewModel by viewModels()

    private val catalogAdapter: CatalogAdapter by lazy { CatalogAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.mCatalogViewModel = mCatalogViewModel

        setupRecyclerView()

        mCatalogViewModel.getAllItems.observe(
            viewLifecycleOwner,
            { data ->
                mCatalogViewModel.checkForCatalogIsEmpty(data)
                catalogAdapter.setData(data)
            }
        )

        return binding.root
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvCatalogItems
        recyclerView.adapter = catalogAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}