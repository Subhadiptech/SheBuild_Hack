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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ersubhadip.domains.dto.adapterModels.ExploreModel
import com.ersubhadip.helpers.Storage
import com.ersubhadip.presenter.adapters.ExploreAdapter
import com.ersubhadip.ww.R
import com.ersubhadip.ww.databinding.FragmentExploreBinding
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment : Fragment() {

    @Inject
    lateinit var ad: ExploreAdapter

    private lateinit var storage: Storage

    private var jList: ArrayList<ExploreModel> = ArrayList()
    private var sList: ArrayList<ExploreModel> = ArrayList()

    private lateinit var binding: FragmentExploreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(layoutInflater)
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

        setVerticalList()
        fetchJobList()
        fetchScList()
        manipulateList(jList)

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
            manipulateList(jList)

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

            //todo:show data
            manipulateList(sList)
        }
    }

    private fun setVerticalList() {
        binding.jobsList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = ad
        }
    }


    private fun manipulateList(list: ArrayList<ExploreModel>) {
        ad.submitList(list)
    }

    private fun fetchJobList() {
        jList.clear()
        jList.add(ExploreModel(1, "Job 1", "Job description 1", ""))
        jList.add(ExploreModel(1, "Job 1", "Job description 1", ""))
        jList.add(ExploreModel(1, "Job 1", "Job description 1", ""))
        jList.add(ExploreModel(1, "Job 1", "Job description 1", ""))
        jList.add(ExploreModel(1, "Job 1", "Job description 1", ""))
        jList.add(ExploreModel(1, "Job 1", "Job description 1", ""))
    }

    private fun fetchScList() {
        sList.clear()
        sList.add(ExploreModel(1, "Scholarships 1", "Scholarships description 1", ""))
        sList.add(ExploreModel(1, "Scholarships 1", "Scholarships description 1", ""))
        sList.add(ExploreModel(1, "Scholarships 1", "Scholarships description 1", ""))
        sList.add(ExploreModel(1, "Scholarships 1", "Scholarships description 1", ""))
        sList.add(ExploreModel(1, "Scholarships 1", "Scholarships description 1", ""))
        sList.add(ExploreModel(1, "Scholarships 1", "Scholarships description 1", ""))
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