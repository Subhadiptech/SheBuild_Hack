package com.ersubhadip.presenter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ersubhadip.ww.R
import com.ersubhadip.ww.databinding.FragmentComplainBinding
import com.ersubhadip.ww.databinding.FragmentExploreBinding

class ComplainFragment : Fragment() {
    lateinit var binding: FragmentComplainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComplainBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //clicks
        binding.complainBtn1.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://ncwapps.nic.in/onlinecomplaintsv2/")
            startActivity(i)
        }

        binding.complainBtn2.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("http://www.shebox.nic.in/")
            startActivity(i)
        }
    }
}