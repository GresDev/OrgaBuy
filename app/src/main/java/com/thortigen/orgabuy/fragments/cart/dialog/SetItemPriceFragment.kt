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
import com.thortigen.orgabuy.databinding.FragmentCartItemPriceDialogBinding
import com.thortigen.orgabuy.viewmodels.CartItemPriceViewModel
import com.thortigen.orgabuy.viewmodels.CartViewModel
import java.lang.Exception
import kotlin.math.roundToInt

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

        binding.priceStart =
            itemPricingArgs.currentItem.price.toString().takeWhile { x -> x != '.' }
        binding.priceEnd =
            itemPricingArgs.currentItem.price.toString().substringAfter(".", "").padEnd(2, '0')


//        var t1 = itemPricingArgs.currentItem.price.toString()
//        var t2 = itemPricingArgs.currentItem.price.toString().takeWhile { x -> x != '.' }
//        var t3 = itemPricingArgs.currentItem.price.toString().substringAfter(".", "").padEnd(2, '0')
//
//        if (t3.length < 2) {
//            t3 += "0"
//        }
//
//
//        var t4 = t3

        binding.tvCancel.setOnClickListener {
            this.dismiss()
        }

        binding.tvOk.setOnClickListener {
            setItemCost()
        }

        binding.currentItem = itemPricingArgs.currentItem

        binding.tiEtItemPriceRub.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                try {
                    mCartItemPriceViewModel.getItemCost(
                        s.toString()?.toDouble() + (binding.tiEtItemPriceKop.text.toString()
                            ?.toDouble()) / 100.0,
                        binding.tiEtItemQuantity.text.toString()?.toDouble()
                    )
                } catch (e: Exception) {

                }
            }
        })


        binding.tiEtItemPriceKop.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                try {
                    mCartItemPriceViewModel.getItemCost(
                        (s.toString().toDouble()) / 100.0 + binding.tiEtItemPriceRub.text.toString()
                            ?.toDouble(),
                        binding.tiEtItemQuantity.text.toString()?.toDouble()
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
                        binding.tiEtItemPriceRub.text.toString()
                            ?.toDouble() + binding.tiEtItemPriceKop.text.toString()
                            ?.toDouble() / 100.0,
                        s.toString()?.toDouble()
                    )
                } catch (e: Exception) {

                }
            }
        })
        return binding.root
    }

    private fun setItemCost() {
        var updatedCartItem = itemPricingArgs.currentItem


        updatedCartItem.price = binding.tiEtItemPriceRub.text.toString()
            ?.toDouble() + binding.tiEtItemPriceKop.text.toString()?.toDouble() / 100.0
        updatedCartItem.quantity = binding.tiEtItemQuantity.text.toString()?.toDouble()

        mCartViewModel.editItem(updatedCartItem)

        this.dismiss()
    }

}