package com.thortigen.orgabuy.fragments.cart

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.thortigen.orgabuy.R
import com.thortigen.orgabuy.databinding.FragmentCartBinding
import com.thortigen.orgabuy.fragments.cart.recyclerview.CartAdapter
import com.thortigen.orgabuy.viewmodels.CartViewModel
import com.thortigen.orgabuy.viewmodels.ShopListViewModel
import kotlin.math.absoluteValue

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val mCartViewModel: CartViewModel by viewModels()

    private val mShopListViewModel: ShopListViewModel by viewModels()

    private val cartAdapter: CartAdapter by lazy { CartAdapter() }

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

        setupRecyclerView()

        mCartViewModel.getAllItems.observe(
            viewLifecycleOwner,
            { data ->
                mCartViewModel.checkForCartIsEmpty(data)
                cartAdapter.setData(data)
            }
        )

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_cart_actionbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_cart_delete_all) {
            confirmCartCleanUp()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmCartCleanUp() {
        val dialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogBox)
        dialog.setPositiveButton("Очистить корзину") { _, _ ->
            mCartViewModel.deleteAllItems()

            mShopListViewModel.cleanUpCartList()

        }
        dialog.setNegativeButton("Отмена") { _, _ -> }
        dialog.setTitle("Очистить корзину?")
        dialog.setMessage("Удаление всех товаров из корзины")
        dialog.create().show()
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