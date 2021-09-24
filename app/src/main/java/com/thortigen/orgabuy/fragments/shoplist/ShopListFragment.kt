package com.thortigen.orgabuy.fragments.shoplist

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.thortigen.orgabuy.R
import com.thortigen.orgabuy.data.models.CartItem
import com.thortigen.orgabuy.data.models.ShopListItem
import com.thortigen.orgabuy.databinding.FragmentShopListBinding
import com.thortigen.orgabuy.fragments.shoplist.recyclerview.ShopListAdapter
import com.thortigen.orgabuy.utils.SwipeToDelete
import com.thortigen.orgabuy.viewmodels.CartViewModel
import com.thortigen.orgabuy.viewmodels.ShopListViewModel

class ShopListFragment : Fragment() {

    private var _binding: FragmentShopListBinding? = null
    private val binding get() = _binding!!

    private val mShopListViewModel: ShopListViewModel by viewModels()

    private val mCartViewModel: CartViewModel by viewModels()

    private val shopListAdapter: ShopListAdapter by lazy { ShopListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentShopListBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.mShopListViewModel = mShopListViewModel

        setupRecyclerView()

        mShopListViewModel.getAllItems.observe(
            viewLifecycleOwner,
            { data ->
                mShopListViewModel.checkForShopListIsEmpty(data)
                shopListAdapter.setData(data.sortedBy { it.name }.sortedBy { it.isInCart })
            }
        )

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_shoplist_actionbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_shoplist_delete_all) {
            confirmShoplistCleanUp()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmShoplistCleanUp() {
        val dialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogBox)
        dialog.setPositiveButton("Очистить список покупок") { _, _ ->
            mCartViewModel.deleteAllItems()
            mShopListViewModel.deleteAllItems()

        }
        dialog.setNegativeButton("Отмена") { _, _ -> }
        dialog.setTitle("Очистить список?")
        dialog.setMessage("Удаление всех товаров из списка и из корзины")
        dialog.create().show()
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvShoplistItems
        recyclerView.adapter = shopListAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallBack = object : SwipeToDelete(ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shopListItemToDelete =
                    shopListAdapter.shopListItemList[viewHolder.absoluteAdapterPosition]
                shopListAdapter.notifyItemRemoved(viewHolder.absoluteAdapterPosition)
                mShopListViewModel.deleteItem(shopListItemToDelete)
                val cartItemToDelete = mCartViewModel.getItemById(shopListItemToDelete.id)
                mCartViewModel.deleteItemById(shopListItemToDelete.id)
                restoreDeletedItem(
                    viewHolder.itemView,
                    shopListItemToDelete,
                    cartItemToDelete,
                    viewHolder.absoluteAdapterPosition
                )
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedItem(
        view: View,
        shopListDeletedItem: ShopListItem,
        cartDeletedItem: CartItem,
        position: Int
    ) {
        val snackbar =
            Snackbar.make(view, "Запись удалена", Snackbar.LENGTH_SHORT)
        snackbar.setAction("Отменить") {
            mShopListViewModel.insertItem(shopListDeletedItem)
            mCartViewModel.insertItem(cartDeletedItem)
            shopListAdapter.notifyItemChanged(position)
        }
        snackbar.setBackgroundTint(resources.getColor(R.color.colorSecondary))
        snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
        snackbar.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}