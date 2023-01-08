package com.ersubhadip.presenter

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ersubhadip.domains.dto.adapterModels.BlogModel
import com.ersubhadip.domains.dto.adapterModels.JobModel
import com.ersubhadip.domains.dto.adapterModels.NGOModel
import com.ersubhadip.domains.dto.adapterModels.StoryModel
import com.ersubhadip.helpers.LocationProvider
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
import java.net.URLEncoder
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var storage: Storage

    private lateinit var binding: FragmentHomeBinding
    private var sList: ArrayList<StoryModel> = ArrayList()
    private var jList: ArrayList<JobModel> = ArrayList()
    private var nList: ArrayList<NGOModel> = ArrayList()
    private var bList: ArrayList<BlogModel> = ArrayList()
    private var latLong: List<String> = java.util.ArrayList()

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


        //permission -------------------------------------------------------------------------------
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //getting location -------------------------------------------------------------------------
            val locationProvider = LocationProvider(context, true)
            latLong = locationProvider.myLatLong()
            storage.lat = latLong[0]
            storage.long = latLong[1]
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 8000
            )
        }

        //layout setting
        setHorizontalList()
        setVerticalList()

        //fetching data
        storyListData()
        fetchNGOList()
        fetchJobList()
        fetchBlogList()


        //clicks
        binding.sosButton.setOnClickListener {
            val dialog: Dialog = Dialog(requireContext())
            val dBinding: SosDialogBinding = SosDialogBinding.inflate(layoutInflater)
            if (storage.number.isNotEmpty()) {
                dBinding.setEmergencyNumber.visibility = View.GONE
                dBinding.sendSos.visibility = View.VISIBLE
            } else {
                dBinding.sendSos.visibility = View.GONE
                dBinding.setEmergencyNumber.visibility = View.VISIBLE
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

            dBinding.sendSos.setOnLongClickListener {
                storage.number = ""
                dBinding.sendSos.visibility = View.GONE
                dBinding.setEmergencyNumber.visibility = View.VISIBLE
                true
            }

            dBinding.sendSos.setOnClickListener {
                dialog.dismiss()
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

            dBinding.exploreBtn.setOnClickListener {
                dialog.dismiss()
                findNavController().navigate(R.id.action_homeFragment_to_complainFragment2)
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

        binding.jobsList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = jAdapter
        }
    }

    private fun setVerticalList() {

        binding.blogList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = bAdapter
        }
    }

    private fun storyListData() {
        sList.clear()
        sList.add(
            StoryModel(
                1,
                "Ann Cotton – Founder of Camfed",
                "Ann Cotton was born in the Welsh city of Cardiff ...",
                listOf("Women", "Motivation"),
                "https://ischoolconnect.com/blog/motivational-success-stories-of-women-you-need-to-read/"
            )
        )
        sList.add(
            StoryModel(
                2,
                "Julia Gillard – Former Prime Minister of Australia",
                "Julia Gillard was born in Wales but relocated  ...",
                listOf("Women", "Education and Equality"),
                "https://ischoolconnect.com/blog/motivational-success-stories-of-women-you-need-to-read/"
            )
        )
        sList.add(
            StoryModel(
                3,
                "Graça Machel – Humanitarian",
                "Graca was born in rural Mozambique as  ...",
                listOf("Women", "Empowerment"),
                "https://ischoolconnect.com/blog/motivational-success-stories-of-women-you-need-to-read/"
            )
        )

        sAdapter.submitList(sList)

    }

    private fun fetchNGOList() {
        nList.clear()
        nList.add(
            NGOModel(
                1,
                "UN Women",
                "The United Nations Entity for Gender Equality ... ",
                "https://www.unwomen.org/en"
            )
        )
        nList.add(
            NGOModel(
                2,
                "Association for Women’s Rights in Development",
                "The Association for Women’s Rights in Development (AWID) is an international  ...",
                "https://www.awid.org/"
            )
        )
        nList.add(
            NGOModel(
                3,
                "Women for Women International",
                "Women for Women International is an international women’s rights organization  ...",
                "https://womenforwomen.org.uk/"
            )
        )

        nAdapter.submitList(nList)
    }

    private fun fetchJobList() {
        jList.clear()
        jList.add(
            JobModel(
                1,
                "System Specialist",
                "Nityo Infotech",
                "https://sg.indeed.com/viewjob?jk=781b1983bfa5f0ed&tk=1gm7f88emk121801&from=serp&vjs=3&advn=3632843039015302&adid=141648178&ad=-6NYlbfkN0AbLEAfUJ_8-EnULjJ15l1k6LfB5RD9Ew3x4SlXgqMriw8oywaHohqK_bfr-pESj-gxWUNFBvQZOb1APZbuXqvrjygV4LxUI9M0O3sKPT2FOOLKgmxX1IZevqtxlqfiLAUb_ozQOaaAC-MwGv2sZkGQHKvu6pjse1VaVyGB-9Vw4TfjaOf1c3MVLHLyt6cFR8w9ZNGE5T2m8cOgAjMaHigy6MUfBIrRsqfPo1YcW--7q7PtEo6kuqdx2ovxLCamNWtXnGP1mLnT7TfCpkvfHL_lIt5mpOtKfAG6g3qV5Lz_5p8W8BEGujyRYgEBzJ8zdgBYP65V1pPV5IQx8oDsXOivxKhnT67B0xUGas_oTg8g6HEjS7hq4ybo&sjdu=5xPDIrPGBD4pzBiYiwI4QQHu7Y6y0Ayu2xhUrlSdCdxVMLjX1bSdgsjenRHDWwjQvziskudbz4zhunWdlttZuzF-K-Ds_mK3cFpwVmR5SQi9dlMq3fi37IJC8kFVP3-uo9Pw-Q5vybpcuHUW_Sdll0e8meG8zHCb5IOq_-hHlBRG6Bkzl9Bw--BGRkM9TwQ93c7s39GDMLYUC10DNTDZSg"
            )
        )
        jList.add(
            JobModel(
                2,
                "Human Resource Executive",
                "My Queen Service Pvt. Ld.",
                "https://sg.indeed.com/pagead/clk?mo=r&ad=-6NYlbfkN0AuWpd06JaTHFjvTB_5q6-0gBNCyrzTNez_CNw5GfFr-Qi3chm1JLIdD9PkehFp3sH5KImTggmis8gT9l_h6k4sKU1vs8qJ1xKkz9cuoNDwcHG8hoTLOFTeiZlVIqeT2Rp3DqHezEtPu2i_L1P9UHwRqL7gUiumNRIx4-kAffD2ZWKNz2I8mT-9ntpbLTXahCSIiJaS3MW-8t7RgTQxHKrYyPpHT_avSQIjwZt_TKi60x6Yfo6SUB-6eMRVFN0f68Cb2yEarjr4rO0SKhEGQ8bfXyo4mufDN4Bpk48-H0Vr-yQJQAOOcd6HX7UZ5-vA5Iv4gAF4jsl14kulvPS9399v8NRkQKf8JvmFcTgSiQXdndfGdUHMJrtUmLNwXqVoO3pLPyvts7NvsqPJUlT1p5AVYcB337Pf_egQHEx1aMxXCjgkxUF-s-OiHamDpFpnHv4zJY8AWzld_h5dKCjaVU0D-TIpy8t019Guzh0SmUxtVWudvPmqpHgjMdRj8w1LdS1g_EqfqHzSIZSWc06avWspu6BJhI6Nfojr9Zvfcp1pUI_iXfRT4hs5h_ocFUPwhV_857Htoh8sEKSO6P8fl1NsUOqkGqyGJ491HbQHtIFgoSlptX6eq9CGGmuVkDvKeK9jXpGBFOCtEzQGUtuPtyFOj-lCm7rgyAsb1w-AbUCTSH54H0mi-qgqvwQ49NP7I6i-zYoZiOPaJnGWIe2d7fiArAD4wYddrWsfhjw9nJG7m9lSbrgvsB5q7QYbr-0iwJ8z5JlUSNR60cIM_KlMegxnUvax7b5HjAI1KtwEqHxHywBJadDWVfKeY3rqye0LC6B2hRG_qqPNqU9l0qUctuEr&xkcb=SoAh-_M3UHWcOoW6Wp0MbzkdCdPP&p=7&fvj=0&vjs=3&tk=1gm7f4uuhk3a6800&jsa=4225&oc=1&sal=1"
            )
        )
        jList.add(
            JobModel(
                3,
                "Management Analysts ",
                "Average salary: \$78,884.00 ",
                "https://mycareer.hsbc.com/en_GB/external/SearchJobs/AW2022ADVSG?pipelineRecordsPerPage=10&#anchor__search-jobs=&utm_source=google.com&utm_medium=paid-search&utm_campaign=asian_wealth_wpb_core-wpb&utm_term=singapore-nationwide&utm_content=na&ss=paid"
            )
        )

        jAdapter.submitList(jList)
    }

    private fun fetchBlogList() {
        bList.clear()
        bList.add(
            BlogModel(
                1,
                "10 Micro-Habits That Will Transform Your Life",
                "With every new job, relationship, habit—you name it—I consider learning in baby steps. You didn’t pop out of your mother’s womb ...",
                "https://witanddelight.com/2022/01/10-micro-habits-that-will-transform-your-life-2/"
            )
        )
        bList.add(
            BlogModel(
                2,
                "A BEAUTIFUL MESS",
                "First started in 2007, A Beautiful Mess has been around a long time. The two sisters behind the site have a passion for  ...",
                "https://abeautifulmess.com/how-to-hygge/"
            )
        )

        bAdapter.submitList(bList)
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

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 8000) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                //getting location -------------------------------------------------------------------------
                val locationProvider = LocationProvider(context, true)
                latLong = locationProvider.myLatLong()
                storage.lat = latLong[0]
                storage.long = latLong[1]
            } else if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_DENIED
            ) {

            }
        }
    }
}