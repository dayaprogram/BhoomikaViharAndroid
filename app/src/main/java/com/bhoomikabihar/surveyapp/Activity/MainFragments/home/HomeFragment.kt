package com.bhoomikabihar.surveyapp.Activity.MainFragments.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhoomikabihar.surveyapp.Activity.ContactUs
import com.bhoomikabihar.surveyapp.Activity.PdfViewActivity
import com.bhoomikabihar.surveyapp.Activity.RegistrationFeedback.FeedbackQuestionActivity
import com.bhoomikabihar.surveyapp.Activity.RegistrationFeedback.RegisterParticipentActivity
import com.bhoomikabihar.surveyapp.Model.DashBoardContain
import com.bhoomikabihar.surveyapp.R
import com.bseb.crossword.ViewAdaptor.DashboardMenuAdaptor
import com.example.dbtagri.RemoteDataRepository.SessionManager
import com.example.dbtagri.ViewAdaptor.RecyclerViewClickInterface

class HomeFragment : Fragment(), RecyclerViewClickInterface {
    lateinit var DashBoardContainList: MutableList<DashBoardContain>
    var userRole = "AC";
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        DashBoardContainList = ArrayList<DashBoardContain>()
        val sharedPrefRegData: SharedPreferences =
            requireActivity().getSharedPreferences(
                getString(R.string.app_name),
                Context.MODE_PRIVATE
            )
        val RemoteVersionCount = sharedPrefRegData.getString("RemoteVersionCount", "")!!
        var sessionManager: SessionManager = SessionManager(requireContext())
        //userRole = sessionManager.fetchAuthACDetails().userRole;
        //a list to store all the products

        //adding some items to our list


        DashBoardContainList.addAll(
            listOf(
                /*DashBoardContain(
                    "OurNews",
                    "हमारी ख़बरें",
                    "",
                    R.drawable.approve
                ),
                DashBoardContain(
                    "BhumikaVarta",
                    "भूमिका वार्ता",
                    "",
                    R.drawable.immigration
                ),*/
                DashBoardContain(
                    "Suggestion",
                    "#He4She",
                    "",
                    R.drawable.hesheicon
                ),

//                    DashBoardContain(
//                        "Certificate",
//                        "सर्टिफिकेट",
//                        "",
//                        R.drawable.bihar_logo_copy
//                    ),
                DashBoardContain(
                    "Index",
                    "About Us",
                    "",
                    R.drawable.aboutusinfo
                ),
                DashBoardContain(
                    "ContactUs",
                    "Contact Us",
                    "",
                    R.drawable.contactus
                ),
                DashBoardContain(
                    "MissionAndVision",
                    "Mission And Vision",
                    "",
                    R.drawable.mission
                ),
                DashBoardContain(
                    "OurTeam",
                    "Our Team",
                    "",
                    R.drawable.teamlink
                ),
                DashBoardContain(
                    "Approach",
                    "Approach",
                    "",
                    R.drawable.approach
                ),
                DashBoardContain(
                    "OrgStruct",
                    "Orgnisation Structure",
                    "",
                    R.drawable.orgnisationstructure
                ),
                DashBoardContain(
                    "ThematicAreas",
                    "Thematic Areas",
                    "",
                    R.drawable.thematicareas
                ),

                )
        )





        val adapter = DashboardMenuAdaptor(requireContext(), DashBoardContainList, this)
        // Fill Recycler View
        val linearLayoutManager2 = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        val recyclerView = root.findViewById(R.id.recyclerViewDashboard) as RecyclerView
        recyclerView.layoutManager = linearLayoutManager2
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        return root
    }

    override fun onItemClick(position: Int) {

        if (DashBoardContainList[position].intentID == "OurNews") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/Home/BhoomikaNewsLetter"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }

        if (DashBoardContainList[position].intentID == "BhumikaVarta") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/Home/TheTransformativeJourney"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }


        if (DashBoardContainList[position].intentID == "Suggestion") {
            val intent = Intent(requireView().context, RegisterParticipentActivity::class.java)
            requireView().context.startActivity(intent)
        }

        if (DashBoardContainList[position].intentID == "ContactUs") {
            val intent = Intent(requireView().context, ContactUs::class.java)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "Index") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/Index"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "MissionAndVision") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/MissionAndVision"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "OurTeam") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/OurTeam"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "OrgStruct") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/OrgStruct"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "Geography") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/Geography"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "ThematicAreas") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/ThematicAreas"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "SuccessStroies") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/SuccessStroies"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "MazaorMileStone") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/MazaorMileStone"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "Partners") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/Partners"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }

    }

    override fun onLongItemClick(position: Int) {

    }
}