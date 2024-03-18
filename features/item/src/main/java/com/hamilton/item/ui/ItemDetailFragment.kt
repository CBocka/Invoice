package com.hamilton.item.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.hamilton.entity.items.Item
import com.hamilton.invoice.MainActivity
import com.hamilton.item.R
import com.hamilton.item.databinding.FragmentItemDetailBinding
import com.hamilton.item.usecase.details.ItemDetailsViewModel

class ItemDetailFragment : Fragment(), MenuProvider {

    private var _binding : FragmentItemDetailBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var item : Item

    private val viewModel : ItemDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)

        val idItem = requireArguments().getString(Item.ITEM_KEY)!!

        item = viewModel.getItemToShow(idItem)

        setUpToolBar()
        initLayout(item)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpToolBar() {
        (requireActivity() as MainActivity).toolbar.apply {
            visibility = View.VISIBLE
        }

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_item_update, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_item_edit -> {
                val bundle = Bundle()
                bundle.putString(Item.UPDATE_KEY, item.id.value)
                findNavController().navigate(R.id.action_itemDetailFragment_to_itemUpdateFragment, bundle)
                true
            }
            else -> false
        }
    }

    private fun initLayout(item: Item){
        binding.tvItemInfoIdValue.text = item.id.value
        binding.tvItemInfoNameValue.text = item.name
        binding.tvItemInfoPriceValue.text = item.price.toString()
        binding.tvItemInfoTypeValue.text = item.type.toString()
        binding.tvItemInfoDescriptionValue.text = item.description
        binding.tvItemInfoTaxValue.text = item.tax.toString()

        if (item.isTaxable)
            binding.tvItemInfoTaxableValue.text = "Sí"
        else
            binding.tvItemInfoTaxableValue.text = "No"

        when(item.photo) {
            "ferrarisf90" -> binding.imgItemInfo.setImageResource(com.hamilton.invoice.R.drawable.ferrarisf90)
            "am_dbx707" -> binding.imgItemInfo.setImageResource(com.hamilton.invoice.R.drawable.am_dbx707)
            "aventador" -> binding.imgItemInfo.setImageResource(com.hamilton.invoice.R.drawable.aventador)
            "corvette" -> binding.imgItemInfo.setImageResource(com.hamilton.invoice.R.drawable.corvette)
            "fordfocus" -> binding.imgItemInfo.setImageResource(com.hamilton.invoice.R.drawable.fordfocus)
            "astonmartinwec" -> binding.imgItemInfo.setImageResource(com.hamilton.invoice.R.drawable.astonmartinwec)
            "benz" -> binding.imgItemInfo.setImageResource(com.hamilton.invoice.R.drawable.benz)
            "camaro" -> binding.imgItemInfo.setImageResource(com.hamilton.invoice.R.drawable.camaro)
            "tesla" -> binding.imgItemInfo.setImageResource(com.hamilton.invoice.R.drawable.tesla)
            "" -> binding.imgItemInfo.setImageResource(com.hamilton.invoice.R.drawable.no_item_img)
        }
    }
}