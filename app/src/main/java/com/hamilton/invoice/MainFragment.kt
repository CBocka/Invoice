package com.hamilton.invoice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hamilton.invoice.databinding.FragmentMainBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        (activity as MainActivity).setAppBarVisible()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btIvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_nav_graph_account)
        }
        binding.btIvItems.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_nav_graph_item)
        }
        binding.btIvCustomer.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_nav_graph_customer)
        }
        binding.btIvTasks.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_nav_graph_task)
        }
        binding.btIvInvoices.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_nav_graph_invoice)
        }
        binding.btIvUsers.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_userListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}