package com.thortigen.orgabuy.fragments.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.thortigen.orgabuy.databinding.FragmentItemViewBinding

class ItemViewFragment : Fragment() {

    private var _binding : FragmentItemViewBinding? = null
    private val binding get() = _binding!!

    val itemViewArgs by navArgs<ItemViewFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentItemViewBinding.inflate(inflater, container, false)
        binding.itemViewArgs = itemViewArgs

        return binding.root
    }

}