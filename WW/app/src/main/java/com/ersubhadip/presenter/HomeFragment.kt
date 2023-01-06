package com.ersubhadip.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ersubhadip.domains.dto.adapterModels.BlogModel
import com.ersubhadip.domains.dto.adapterModels.JobModel
import com.ersubhadip.domains.dto.adapterModels.NGOModel
import com.ersubhadip.domains.dto.adapterModels.StoryModel
import com.ersubhadip.presenter.adapters.BlogAdapter
import com.ersubhadip.presenter.adapters.JobAdapter
import com.ersubhadip.presenter.adapters.NGOAdapter
import com.ersubhadip.presenter.adapters.StoryAdapter
import com.ersubhadip.ww.R
import com.ersubhadip.ww.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var sList: List<StoryModel>
    private lateinit var jList: List<JobModel>
    private lateinit var nList: List<NGOModel>
    private lateinit var bList: List<BlogModel>

    //adapters

    @Inject
    lateinit var sAdapter: StoryAdapter

    @Inject
    lateinit var jAdapter: JobAdapter

    @Inject
    lateinit var nAdapter: NGOAdapter

    @Inject
    lateinit var bAdapter: BlogAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //clicks
        binding.exploreJobs.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_exploreFragment)
        }
    }

    private fun setHorizontalList() {

    }

    private fun setVerticalList() {

    }


    private fun dummyList() {

    }
}