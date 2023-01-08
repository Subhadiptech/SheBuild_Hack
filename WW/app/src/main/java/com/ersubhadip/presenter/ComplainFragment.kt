package com.ersubhadip.presenter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ersubhadip.helpers.Storage
import com.ersubhadip.ww.R
import com.ersubhadip.ww.databinding.FragmentComplainBinding
import com.ersubhadip.ww.databinding.FragmentExploreBinding
import java.net.URLEncoder

class ComplainFragment : Fragment() {
    private lateinit var storage: Storage
    lateinit var binding: FragmentComplainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComplainBinding.inflate(layoutInflater)
        storage = Storage(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sosBtn.setOnClickListener {
            if (storage.lat.isNotEmpty() && storage.long.isNotEmpty()) {
                sos()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Location Permission not provided",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


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

    @SuppressLint("QueryPermissionsNeeded")
    private fun sos() {
        Toast.makeText(
            requireContext(),
            "Message sent to ${storage.number}",
            Toast.LENGTH_SHORT
        ).show()

        try {
            val packageManager = requireContext().packageManager
            val i = Intent(Intent.ACTION_VIEW)
            val url =
                "https://wa.me/91${storage.number}" + "?text=" + URLEncoder.encode(
                    "https://www.google.com/maps?t=m&q=loc:${storage.lat},${storage.long}",
                    "utf-8"
                )
            i.setPackage("com.whatsapp")
            i.data = Uri.parse(url)
            requireContext().startActivity(i)

        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Whatsapp not found!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}