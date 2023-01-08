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
import com.ersubhadip.domains.dto.adapterModels.JobModel
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
        jList.add(
            ExploreModel(
                1,
                "System Specialist",
                "Nityo Infotech",
                "https://sg.indeed.com/viewjob?jk=781b1983bfa5f0ed&tk=1gm7f88emk121801&from=serp&vjs=3&advn=3632843039015302&adid=141648178&ad=-6NYlbfkN0AbLEAfUJ_8-EnULjJ15l1k6LfB5RD9Ew3x4SlXgqMriw8oywaHohqK_bfr-pESj-gxWUNFBvQZOb1APZbuXqvrjygV4LxUI9M0O3sKPT2FOOLKgmxX1IZevqtxlqfiLAUb_ozQOaaAC-MwGv2sZkGQHKvu6pjse1VaVyGB-9Vw4TfjaOf1c3MVLHLyt6cFR8w9ZNGE5T2m8cOgAjMaHigy6MUfBIrRsqfPo1YcW--7q7PtEo6kuqdx2ovxLCamNWtXnGP1mLnT7TfCpkvfHL_lIt5mpOtKfAG6g3qV5Lz_5p8W8BEGujyRYgEBzJ8zdgBYP65V1pPV5IQx8oDsXOivxKhnT67B0xUGas_oTg8g6HEjS7hq4ybo&sjdu=5xPDIrPGBD4pzBiYiwI4QQHu7Y6y0Ayu2xhUrlSdCdxVMLjX1bSdgsjenRHDWwjQvziskudbz4zhunWdlttZuzF-K-Ds_mK3cFpwVmR5SQi9dlMq3fi37IJC8kFVP3-uo9Pw-Q5vybpcuHUW_Sdll0e8meG8zHCb5IOq_-hHlBRG6Bkzl9Bw--BGRkM9TwQ93c7s39GDMLYUC10DNTDZSg"
            )
        )
        jList.add(
            ExploreModel(
                2,
                "Human Resource Executive",
                "My Queen Service Pvt. Ld.",
                "https://sg.indeed.com/pagead/clk?mo=r&ad=-6NYlbfkN0AuWpd06JaTHFjvTB_5q6-0gBNCyrzTNez_CNw5GfFr-Qi3chm1JLIdD9PkehFp3sH5KImTggmis8gT9l_h6k4sKU1vs8qJ1xKkz9cuoNDwcHG8hoTLOFTeiZlVIqeT2Rp3DqHezEtPu2i_L1P9UHwRqL7gUiumNRIx4-kAffD2ZWKNz2I8mT-9ntpbLTXahCSIiJaS3MW-8t7RgTQxHKrYyPpHT_avSQIjwZt_TKi60x6Yfo6SUB-6eMRVFN0f68Cb2yEarjr4rO0SKhEGQ8bfXyo4mufDN4Bpk48-H0Vr-yQJQAOOcd6HX7UZ5-vA5Iv4gAF4jsl14kulvPS9399v8NRkQKf8JvmFcTgSiQXdndfGdUHMJrtUmLNwXqVoO3pLPyvts7NvsqPJUlT1p5AVYcB337Pf_egQHEx1aMxXCjgkxUF-s-OiHamDpFpnHv4zJY8AWzld_h5dKCjaVU0D-TIpy8t019Guzh0SmUxtVWudvPmqpHgjMdRj8w1LdS1g_EqfqHzSIZSWc06avWspu6BJhI6Nfojr9Zvfcp1pUI_iXfRT4hs5h_ocFUPwhV_857Htoh8sEKSO6P8fl1NsUOqkGqyGJ491HbQHtIFgoSlptX6eq9CGGmuVkDvKeK9jXpGBFOCtEzQGUtuPtyFOj-lCm7rgyAsb1w-AbUCTSH54H0mi-qgqvwQ49NP7I6i-zYoZiOPaJnGWIe2d7fiArAD4wYddrWsfhjw9nJG7m9lSbrgvsB5q7QYbr-0iwJ8z5JlUSNR60cIM_KlMegxnUvax7b5HjAI1KtwEqHxHywBJadDWVfKeY3rqye0LC6B2hRG_qqPNqU9l0qUctuEr&xkcb=SoAh-_M3UHWcOoW6Wp0MbzkdCdPP&p=7&fvj=0&vjs=3&tk=1gm7f4uuhk3a6800&jsa=4225&oc=1&sal=1"
            )
        )
        jList.add(
            ExploreModel(
                3,
                "Management Analysts ",
                "Average salary: \$78,884.00 ",
                "https://mycareer.hsbc.com/en_GB/external/SearchJobs/AW2022ADVSG?pipelineRecordsPerPage=10&#anchor__search-jobs=&utm_source=google.com&utm_medium=paid-search&utm_campaign=asian_wealth_wpb_core-wpb&utm_term=singapore-nationwide&utm_content=na&ss=paid"
            )
        )

    }

    private fun fetchScList() {
        sList.clear()
        sList.add(
            ExploreModel(
                1,
                "Winnie Sun Scholarship Program for Girl child",
                "Lotus Petal Foundation is a for purpose organization located in Gurgaon which has been working since 2011 towards getting less privileged children into the formal job market based on its programs in the areas of education, healthcare, and skill development.",
                "https://www.lotuspetalfoundation.org/winnie-sun-scholarship-program-for-girl-child/"
            )
        )
        sList.add(
            ExploreModel(
                2,
                "International Scholarship",
                "Scholarships by University of Tasmania",
                "https://www.utas.edu.au/study/scholarships-fees-and-costs/international-scholarships?gclsrc=ds&gclsrc=ds"
            )
        )
        sList.add(
            ExploreModel(
                3,
                "CBSE Udaan",
                "organizations offer several Awards opportunities to uplift the condition of Girls education in India.",
                "https://karnatakastateopenuniversity.in/"
            )
        )
        sList.add(
            ExploreModel(
                4,
                "AICTE Pragati Scholarship for Girls",
                "o provide encouragement and support to Girl Child to pursue technical education “Pragati Scholarship” has been launched by the MHRD ...",
                "https://www.aicte-india.org/downloads/About%20the%20Scheme_ps_16.pdf"
            )
        )

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