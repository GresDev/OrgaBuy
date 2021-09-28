package com.thortigen.orgabuy.fragments.item

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.thortigen.orgabuy.R
import com.thortigen.orgabuy.data.models.CatalogItem
import com.thortigen.orgabuy.databinding.FragmentItemViewBinding
import com.thortigen.orgabuy.viewmodels.CartViewModel
import com.thortigen.orgabuy.viewmodels.CatalogViewModel
import com.thortigen.orgabuy.viewmodels.ShopListViewModel

class ItemViewFragment : Fragment() {

    private var _binding: FragmentItemViewBinding? = null
    private val binding get() = _binding!!

    val itemViewArgs by navArgs<ItemViewFragmentArgs>()

    private val mCatalogViewModel: CatalogViewModel by viewModels()
    private val mShopListViewModel: ShopListViewModel by viewModels()
    private val mCartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentItemViewBinding.inflate(inflater, container, false)
        binding.itemViewArgs = itemViewArgs

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item_view_actionbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_view_edit) {
            val action =
                ItemViewFragmentDirections.actionItemViewFragmentToItemEditFragment(itemViewArgs.currentItem)
            findNavController().navigate(action)
        }

        if (item.itemId == R.id.menu_item_view_delete) {
            confirmDeleteItem(itemViewArgs.currentItem)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteItem(catalogItem: CatalogItem) {
        val dialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogBox)
        dialog.setPositiveButton("Удалить запись") { _, _ ->
            mCatalogViewModel.deleteItem(catalogItem)
            mShopListViewModel.deleteItemById(catalogItem.id)
            mCartViewModel.deleteItemById(catalogItem.id)
            findNavController().navigate(R.id.catalogFragment)
        }
        dialog.setNegativeButton("Отмена") { _, _ -> }
        dialog.setTitle("Удалить запись?")
        dialog.setMessage("Удаление записи из каталога")
        dialog.create().show()
    }


}