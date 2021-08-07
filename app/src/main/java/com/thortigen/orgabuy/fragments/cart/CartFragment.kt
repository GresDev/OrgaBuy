package com.thortigen.orgabuy.fragments.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.thortigen.orgabuy.R
import com.thortigen.orgabuy.databinding.FragmentCartBinding
import com.thortigen.orgabuy.fragments.cart.recyclerview.CartAdapter
import com.thortigen.orgabuy.viewmodels.CartViewModel

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val mCartViewModel : CartViewModel by viewModels()

    private val cartAdapter : CartAdapter by lazy {CartAdapter()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.mCartViewModel = mCartViewModel

        mCartViewModel.getAllItems.observe(
            viewLifecycleOwner,
            {data -> mCartViewModel.checkForCartIsEmpty(data)
            cartAdapter.setData(data)
            }
        )

        return binding.root
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvCartItems
        recyclerView.adapter = cartAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}