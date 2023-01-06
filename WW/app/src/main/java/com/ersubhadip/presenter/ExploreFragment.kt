package com.ersubhadip.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.ersubhadip.ww.R
import com.ersubhadip.ww.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {
    lateinit var binding: FragmentExploreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //custom click on tabs
        binding.tab1.setOnClickListener {
            binding.tab1.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.tab_filled
                )
            )
            binding.tab2.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.tab_hollow
                )
            )
            //todo:show data

        }

        binding.tab2.setOnClickListener {
            binding.tab1.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.tab_hollow
                )
            )
            binding.tab2.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.tab_filled
                )
            )
        }
    }
}