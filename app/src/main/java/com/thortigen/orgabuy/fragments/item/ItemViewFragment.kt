package com.thortigen.orgabuy.fragments.item

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.thortigen.orgabuy.R
import com.thortigen.orgabuy.databinding.FragmentItemViewBinding

class ItemViewFragment : Fragment() {

    private var _binding: FragmentItemViewBinding? = null
    private val binding get() = _binding!!

    val itemViewArgs by navArgs<ItemViewFragmentArgs>()

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
            val action = ItemViewFragmentDirections.actionItemViewFragmentToItemEditFragment(itemViewArgs.currentItem)
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }

}