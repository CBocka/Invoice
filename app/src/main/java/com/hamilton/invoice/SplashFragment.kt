package com.hamilton.invoice

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hamilton.invoice.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding : FragmentSplashBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        (activity as MainActivity).setAppBarGone()

        var r = Runnable {
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        }
        Handler(Looper.getMainLooper()).postDelayed(r, 2000)
    }
}