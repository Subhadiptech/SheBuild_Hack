package com.ersubhadip.presenter

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ersubhadip.domains.dto.adapterModels.BlogModel
import com.ersubhadip.domains.dto.adapterModels.JobModel
import com.ersubhadip.domains.dto.adapterModels.NGOModel
import com.ersubhadip.domains.dto.adapterModels.StoryModel
import com.ersubhadip.helpers.Storage
import com.ersubhadip.presenter.adapters.BlogAdapter
import com.ersubhadip.presenter.adapters.JobAdapter
import com.ersubhadip.presenter.adapters.NGOAdapter
import com.ersubhadip.presenter.adapters.StoryAdapter
import com.ersubhadip.ww.R
import com.ersubhadip.ww.databinding.FragmentHomeBinding
import com.ersubhadip.ww.databinding.SosDialogBinding
import com.ersubhadip.ww.databinding.StoriesSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var storage: Storage

    private lateinit var binding: FragmentHomeBinding
    private var sList: ArrayList<StoryModel> = ArrayList()
    private var jList: ArrayList<JobModel> = ArrayList()
    private var nList: ArrayList<NGOModel> = ArrayList()
    private var bList: ArrayList<BlogModel> = ArrayList()

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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        storage = Storage(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //layout setting
        setHorizontalList()
        setVerticalList()

        //fetching data
        storyListData()


        //clicks
        binding.sosButton.setOnClickListener {
            val dialog: Dialog = Dialog(requireContext())
            val dBinding: SosDialogBinding = SosDialogBinding.inflate(layoutInflater)
            if (storage.isNewUser()) {
                dBinding.sendSos.visibility = View.GONE
                dBinding.setEmergencyNumber.visibility = View.VISIBLE
            } else {
                dBinding.setEmergencyNumber.visibility = View.GONE
                dBinding.sendSos.visibility = View.VISIBLE
            }
            dialog.apply {
                setContentView(dBinding.root)
                setCancelable(true)
                show()
            }

            dBinding.setEmergencyNumber.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    if (s.toString().isNotEmpty() && s.toString().length == 10) {
                        storage.number = s.toString()
                        dBinding.setEmergencyNumber.visibility = View.GONE
                        dBinding.sendSos.visibility = View.VISIBLE
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {

                }
            })

            dBinding.sendSos.setOnClickListener {
                dialog.dismiss()
                sos()
            }

            dBinding.exploreBtn.setOnClickListener {
                dialog.dismiss()
                findNavController().navigate(R.id.action_homeFragment_to_policeFragment)
            }


        }

        binding.exploreJobs.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_exploreFragment)
        }

        binding.addStories.setOnClickListener {
            val sheet: BottomSheetDialog = BottomSheetDialog(requireContext())
            val sheetBinding: StoriesSheetBinding = StoriesSheetBinding.inflate(layoutInflater)
            sheet.apply {
                setContentView(sheetBinding.root)
                setCancelable(true)
                show()
            }

            //add events
            sheetBinding.submitStories.setOnClickListener {
                if (sheetBinding.title.text.isNotEmpty() && sheetBinding.desc.text.isNotEmpty()) {
                    //todo:send to backend and show toast
                    Toast.makeText(
                        requireContext(),
                        "The story has been sent for review!",
                        Toast.LENGTH_SHORT
                    ).show()
                    sheet.dismiss()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please fill all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            //subscribe
            binding.subscribeBtn.setOnClickListener {
                if (binding.subsEmail.text.isNotEmpty() && isValidEmail(binding.subsEmail.text)) {
                    Toast.makeText(
                        requireContext(),
                        "Subscribed",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Invalid email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun setHorizontalList() {
        binding.storiesList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = sAdapter
        }


        binding.campaignsList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = nAdapter
        }
    }

    private fun setVerticalList() {
        binding.jobsList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = jAdapter
        }

        binding.blogList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = bAdapter
        }
    }

    private fun storyListData() {
        sList.add(
            StoryModel(
                1,
                "Story 1",
                "This is the description of story 1",
                listOf("Women", "Success")
            )
        )
        sList.add(
            StoryModel(
                2,
                "Story 2",
                "This is the description of story 2",
                listOf("Women", "Success")
            )
        )
        sList.add(
            StoryModel(
                3,
                "Story 3",
                "This is the description of story 3",
                listOf("Women", "Success")
            )
        )
        sList.add(
            StoryModel(
                4,
                "Story 4",
                "This is the description of story 4",
                listOf("Women", "Success")
            )
        )
        sList.add(
            StoryModel(
                5,
                "Story 5",
                "This is the description of story 5",
                listOf("Women", "Success")
            )
        )
        sList.add(
            StoryModel(
                6,
                "Story 6",
                "This is the description of story 6",
                listOf("Women", "Success")
            )
        )

        sAdapter.submitList(sList)

    }

    private fun sos() {

    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
}