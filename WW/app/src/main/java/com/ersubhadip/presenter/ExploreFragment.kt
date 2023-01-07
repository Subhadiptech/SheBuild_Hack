package com.ersubhadip.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ersubhadip.domains.dto.adapterModels.ExploreModel
import com.ersubhadip.presenter.adapters.ExploreAdapter
import com.ersubhadip.ww.R
import com.ersubhadip.ww.databinding.FragmentExploreBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment : Fragment() {

    @Inject
    lateinit var adapter: ExploreAdapter

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
            adapter = adapter
        }
    }


    private fun manipulateList(list: ArrayList<ExploreModel>) {
        adapter.submitList(list)
    }
}