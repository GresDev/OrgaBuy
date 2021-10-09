package com.thortigen.orgabuy.fragments.cart.dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.thortigen.orgabuy.data.models.CartItem
import com.thortigen.orgabuy.databinding.FragmentCartItemPriceDialogBinding
import com.thortigen.orgabuy.fragments.cart.CartFragment
import com.thortigen.orgabuy.fragments.cart.CartFragmentDirections
import com.thortigen.orgabuy.viewmodels.CartItemPriceViewModel
import com.thortigen.orgabuy.viewmodels.CartViewModel
import java.lang.Exception

class SetItemPriceFragment : DialogFragment() {

    private var _binding: FragmentCartItemPriceDialogBinding? = null
    private val binding get() = _binding!!

    private val mCartItemPriceViewModel: CartItemPriceViewModel by viewModels()

    private val mCartViewModel: CartViewModel by viewModels()

    private val itemPricingArgs by navArgs<SetItemPriceFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCartItemPriceDialogBinding.inflate(inflater, container, false)

        binding.cartItemViewModel = mCartItemPriceViewModel
        binding.lifecycleOwner = this

        binding.tvCancel.setOnClickListener {
            findNavController().navigate(SetItemPriceFragmentDirections.actionSetItemPriceFragmentToCartFragment())
        }

        binding.tvOk.setOnClickListener {
            setItemCost()
        }

        binding.currentItem = itemPricingArgs.currentItem

        binding.tiEtItemPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                try {
                    mCartItemPriceViewModel.getItemCost(
                        s.toString().toDouble(),
                        binding.tiEtItemQuantity.text.toString().toDouble()
                    )
                } catch (e: Exception) {

                }
            }
        })

        binding.tiEtItemQuantity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                try {
                    mCartItemPriceViewModel.getItemCost(
                        binding.tiEtItemPrice.text.toString().toDouble(),
                        s.toString().toDouble()
                    )
                } catch (e: Exception) {

                }
            }
        })
        return binding.root
    }

    private fun setItemCost() {
        var updatedCartItem = itemPricingArgs.currentItem
        updatedCartItem.price = binding.tiEtItemPrice.text.toString().toDouble()
        updatedCartItem.quantity = binding.tiEtItemQuantity.text.toString().toDouble()

        mCartViewModel.editItem(updatedCartItem)

        findNavController().navigate(SetItemPriceFragmentDirections.actionSetItemPriceFragmentToCartFragment())
    }

}