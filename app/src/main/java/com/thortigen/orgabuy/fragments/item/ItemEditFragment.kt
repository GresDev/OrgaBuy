package com.thortigen.orgabuy.fragments.item

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.thortigen.orgabuy.R
import com.thortigen.orgabuy.R.*
import com.thortigen.orgabuy.data.models.CartItem
import com.thortigen.orgabuy.data.models.CatalogItem
import com.thortigen.orgabuy.data.models.ShopListItem
import com.thortigen.orgabuy.databinding.FragmentItemEditBinding
import com.thortigen.orgabuy.viewmodels.CartViewModel
import com.thortigen.orgabuy.viewmodels.CatalogViewModel
import com.thortigen.orgabuy.viewmodels.ShopListViewModel
import java.util.*

class ItemEditFragment : Fragment() {

    private var _binding: FragmentItemEditBinding? = null

    private val binding get() = _binding!!

    private val mShopListViewModel: ShopListViewModel by viewModels()

    private val mCatalogViewModel: CatalogViewModel by viewModels()

    private val mCartViewModel: CartViewModel by viewModels()

    private val args by navArgs<ItemEditFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentItemEditBinding.inflate(inflater, container, false)
        binding.args = args

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item_edit_actionbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_edit_save) {
            editItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editItem() {

        val mName = binding.etItemEditName.text.toString()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val mDescription = binding.etItemEditDescription.text.toString()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val validation = verifyItemInput(mName)
        if (validation) {
            val editedItem = CatalogItem(
                args.currentItem.id,
                mName,
                mDescription
            )

            updateItem(editedItem, R.id.action_itemEditFragment_to_catalogFragment)

        } else {
            showToast("Поле \"Наименование\" не заполнено!")
        }
    }

    private fun updateItem(updatedItem: CatalogItem, action: Int) {

        if (updatedItem.name == args.currentItem.name && args.currentItem.description == updatedItem.description) {
            findNavController().navigate(action)
            return
        }

        if (mCatalogViewModel.getItemByName(updatedItem.name) == null || (updatedItem.name == args.currentItem.name && args.currentItem.description != updatedItem.description)) {
            mCatalogViewModel.editItem(updatedItem)

            if (mShopListViewModel.checkForItemIsInList(updatedItem.id)) {

                mShopListViewModel.editItem(
                    ShopListItem(
                        updatedItem.id,
                        updatedItem.name,
                        updatedItem.description,
                        mShopListViewModel.getItemById(updatedItem.id).isInCart
                    )
                )

                mCartViewModel.editItem(
                    CartItem(
                        updatedItem.id,
                        updatedItem.name,
                        updatedItem.description,
                        0.0,
                        0.0

                    )
                )
            }
            showToast("Запись обновлена")
            findNavController().navigate(action)
        } else {
            showToast("Такая запись уже есть")
        }
    }

    private fun verifyItemInput(name: String): Boolean {
        if (name.isEmpty()) {
            return false
        }
        return true
    }

    private fun showToast(message: String) {
        val toast =
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.show()
    }

}