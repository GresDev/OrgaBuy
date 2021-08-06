package com.thortigen.orgabuy.fragments.shoplist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.thortigen.orgabuy.R
import com.thortigen.orgabuy.databinding.FragmentShopListBinding
import com.thortigen.orgabuy.fragments.shoplist.recyclerview.ShopListAdapter
import com.thortigen.orgabuy.viewmodels.ShopListViewModel

class ShopListFragment : Fragment() {

    private var _binding : FragmentShopListBinding? = null
    private val binding get() = _binding!!

    private val mShoplistViewModel : ShopListViewModel by viewModels()

    private val shopListAdapter : ShopListAdapter by lazy { ShopListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShopListBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.mShopListViewModel = mShoplistViewModel

        setupRecyclerView()

        mShoplistViewModel.getAllItems.observe(
            viewLifecycleOwner,
            {
                data -> mShoplistViewModel.checkForShopListIsEmpty(data)
                shopListAdapter.setData(data)
            }
        )

        return binding.root
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvShoplistItems
        recyclerView.adapter = shopListAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}