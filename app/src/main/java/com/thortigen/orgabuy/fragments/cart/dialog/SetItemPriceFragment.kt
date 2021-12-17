package com.thortigen.orgabuy.fragments.cart.dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.thortigen.orgabuy.R
import com.thortigen.orgabuy.databinding.FragmentCartItemPriceDialogBinding
import com.thortigen.orgabuy.utils.hideKeyboard
import com.thortigen.orgabuy.viewmodels.CartItemPriceViewModel
import com.thortigen.orgabuy.viewmodels.CartViewModel
import java.lang.Exception
import kotlin.math.roundToInt

class SetItemPriceFragment : Fragment() {

    private var _binding: FragmentCartItemPriceDialogBinding? = null
    private val binding get() = _binding!!

    private val mCartItemPriceViewModel: CartItemPriceViewModel by viewModels()

    private val mCartViewModel: CartViewModel by viewModels()

    private val itemPricingArgs by navArgs<SetItemPriceFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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


        binding.quantityStart =
            itemPricingArgs.currentItem.quantity.toString().takeWhile { x -> x != '.' }
        binding.quantityEnd =
            itemPricingArgs.currentItem.quantity.toString().substringAfter(".", "").padEnd(3, '0')


        binding.currentItem = itemPricingArgs.currentItem

        binding.tiEtItemPriceStart.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                try {
                    mCartItemPriceViewModel.getItemCost(
                        s.toString()?.toDouble() + (binding.tiEtItemPriceEnd.text.toString()
                            ?.toDouble()) / 100.0,
                        binding.tiEtItemQuantityStart.text.toString()
                            ?.toDouble() + quantityEndNormalize(binding.tiEtItemQuantityEnd.text.toString()) / 1000.0
                    )
                } catch (e: Exception) {

                }
            }
        })


        binding.tiEtItemPriceEnd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                try {
                    mCartItemPriceViewModel.getItemCost(
                        (s.toString()
                            .toDouble()) / 100.0 + binding.tiEtItemPriceStart.text.toString()
                            ?.toDouble(),
                        binding.tiEtItemQuantityStart.text.toString()
                            ?.toDouble() + quantityEndNormalize(binding.tiEtItemQuantityEnd.text.toString()) / 1000.0
                    )
                } catch (e: Exception) {

                }
            }
        })


        binding.tiEtItemQuantityStart.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                try {
                    mCartItemPriceViewModel.getItemCost(
                        binding.tiEtItemPriceStart.text.toString()
                            ?.toDouble() + binding.tiEtItemPriceEnd.text.toString()
                            ?.toDouble() / 100.0,
                        s.toString()
                            ?.toDouble() + quantityEndNormalize(binding.tiEtItemQuantityEnd.text.toString()) / 1000.0
                    )
                } catch (e: Exception) {

                }
            }
        })

        binding.tiEtItemQuantityEnd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                try {
                    mCartItemPriceViewModel.getItemCost(
                        binding.tiEtItemPriceStart.text.toString()
                            ?.toDouble() + binding.tiEtItemPriceEnd.text.toString()
                            ?.toDouble() / 100.0,
                        binding.tiEtItemQuantityStart.text.toString()
                            ?.toDouble() + quantityEndNormalize(s.toString()) / 1000.0
                    )
                } catch (e: Exception) {

                }
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun quantityEndNormalize(quantityEnd: String): Double {
        when (quantityEnd.length) {
            1 -> return quantityEnd.toDouble() * 100.0
            2 -> return quantityEnd.toDouble() * 10.0
            3 -> return quantityEnd.toDouble()
        }
        return 0.0
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item_enter_price_actionbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_enter_price) {
            setItemCost()
            findNavController().navigate(R.id.action_setItemPriceFragment_to_cartFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setItemCost() {
        val updatedCartItem = itemPricingArgs.currentItem


        updatedCartItem.price = binding.tiEtItemPriceStart.text.toString()
            ?.toDouble() + binding.tiEtItemPriceEnd.text.toString()?.toDouble() / 100.0
        updatedCartItem.quantity = binding.tiEtItemQuantityStart.text.toString()
            ?.toDouble() + quantityEndNormalize(binding.tiEtItemQuantityEnd.text.toString()) / 1000.0

        mCartViewModel.editItem(updatedCartItem)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        view?.hideKeyboard()
    }

}