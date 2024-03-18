package com.hamilton.item.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamilton.item.adapter.ItemListAdapter
import com.hamilton.entity.items.Item
import com.hamilton.invoice.MainActivity
import com.hamilton.invoice.base.BaseFragmentDialog
import com.hamilton.item.R
import com.hamilton.item.databinding.FragmentItemListBinding
import com.hamilton.item.usecase.list.ItemListState
import com.hamilton.item.usecase.list.ItemListViewModel

class ItemListFragment : Fragment(), MenuProvider {

    private var _binding : FragmentItemListBinding? = null
    private val binding get() = _binding!!

    private val viewModel : ItemListViewModel by viewModels()

    private lateinit var itemAdapter : ItemListAdapter

    private val TAG : String = "ITEM_TAG"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var appBarConfiguration =
            AppBarConfiguration.Builder(R.id.itemListFragment)
                .setOpenableLayout((requireActivity() as MainActivity).drawer)
                .build()

        NavigationUI.setupWithNavController(
            (requireActivity() as MainActivity).toolbar,
            findNavController(),
            appBarConfiguration
        )

        setUpToolBar()

        setUpItemsRecycler()

        binding.fab.setOnClickListener {
            findNavController().navigate(com.hamilton.item.R.id.action_itemListFragment_to_itemCreationFragment)
        }

        viewModel.getState().observe(viewLifecycleOwner, Observer{
            when(it) {
                ItemListState.NoData -> onNoData()
                is ItemListState.Success -> onSuccess()
            }
        })

        viewModel.allItems.observe(viewLifecycleOwner) {
            val items = getListManagedByPreferences()

            it.let { itemAdapter.submitList(items) }
            viewModel.getItemList(items)
        }
    }

    private fun getListManagedByPreferences() : List<Item> {
        val items = if (viewModel.allItems.value == null)
            arrayListOf()
        else
            viewModel.allItems.value!!

        return viewModel.getListFilterByPreference(
            viewModel.getListOrderByPreference(items))
    }

    private fun setUpToolBar() {
        (requireActivity() as MainActivity).toolbar.apply {
            visibility = View.VISIBLE
        }

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_item_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_refresh -> {
                viewModel.getItemList(getListManagedByPreferences())
                true
            }

            R.id.action_sort -> {
                itemAdapter.sortByName()
                true
            }

            else -> false
        }
    }

    private fun onNoData() {
        binding.rvItemList.visibility = View.GONE
        binding.animationViewItemList.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.VISIBLE
        binding.tvNoDataSubtitle.visibility = View.VISIBLE
    }

    private fun onSuccess() {
        binding.rvItemList.visibility = View.VISIBLE
        binding.animationViewItemList.visibility = View.GONE
        binding.tvNoData.visibility = View.GONE
        binding.tvNoDataSubtitle.visibility = View.GONE
    }

    private fun setUpItemsRecycler() {
        with (binding.rvItemList) {
            itemAdapter = ItemListAdapter({onItemSelected(it)}, {onLongItemSelected(it)})
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = itemAdapter
        }
    }

    private fun onItemSelected(item: Item){
        val bundle = Bundle()
        bundle.putString(Item.ITEM_KEY, item.id.value)
        findNavController().navigate(R.id.action_itemListFragment_to_itemDetailFragment, bundle)
    }

    private fun onLongItemSelected(item: Item) : Boolean {
        val dialog = BaseFragmentDialog.newInstance(
            getString(R.string.dialogDeleteItem_title),
            getString(R.string.dialogDeleteItem_message)
        )

        dialog.show((context as AppCompatActivity).supportFragmentManager, TAG)

        dialog.parentFragmentManager.setFragmentResultListener(
            BaseFragmentDialog.request, viewLifecycleOwner
        ) { _, bundle ->
            val result = bundle.getBoolean(BaseFragmentDialog.result)
            if (result) {
                viewModel.deleteItemFromList(item)

                Toast.makeText(requireContext(), "Elemento borrado con éxito", Toast.LENGTH_SHORT).show()
            }
        }

        return true
    }
}
