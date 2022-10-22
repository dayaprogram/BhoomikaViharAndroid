package com.bhoomikabihar.surveyapp.Activity.MainFragments.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhoomikabihar.surveyapp.Activity.ContactUs
import com.bhoomikabihar.surveyapp.Activity.PaymentGetway.DonationActivity
import com.bhoomikabihar.surveyapp.Activity.PdfViewActivity
import com.bhoomikabihar.surveyapp.Activity.RegistrationFeedback.AboutHeSheActivity
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

                DashBoardContain(
                    "Suggestion",
                    "#He4She",
                    "He+She Feedback",
                    R.drawable.heforshe
                ),
                DashBoardContain(
                    "Donate",
                    "Donate",
                    "Help To Improve",
                    R.drawable.donatebhoomika
                ),
                DashBoardContain(
                    "Index",
                    "About Us",
                    "Explore Who We are?",
                    R.drawable.avoutbhoomika
                ),
                DashBoardContain(
                    "ContactUs",
                    "Contact Us",
                    "Get in touch with us",
                    R.drawable.contactbhoomikapage
                ),
                DashBoardContain(
                    "MissionAndVision",
                    "Mission And Vision",
                    "Development and human rights",
                    R.drawable.missionandvishionbh
                ),
                DashBoardContain(
                    "OurTeam",
                    "Our Team",
                    "Bhoomika Vihar Team",
                    R.drawable.bhoomikateam
                ),
                DashBoardContain(
                    "Approach",
                    "Approach",
                    "Steps toward accomplishment",
                    R.drawable.approach
                ),
                DashBoardContain(
                    "OrgStruct",
                    "Organisation Structure",
                    "Define the goals",
                    R.drawable.organo
                ),
                DashBoardContain(
                    "ThematicAreas",
                    "Thematic Areas",
                    "Thematic Areas of Interventions",
                    R.drawable.thematicareas
                ),
                DashBoardContain(
                    "Geography",
                    "Geographical Representation",
                    "Representation of geography on map",
                    R.drawable.geography
                ),
                DashBoardContain(
                    "MazaorMilestone",
                    "Mazaor Milestone",
                    "achievements of the organization",
                    R.drawable.mazaormilestone
                ),
                DashBoardContain(
                    "Certifications",
                    "Certifications And Compliances",
                    "Complete details of Certifications, etc.",
                    R.drawable.certificateandcomp
                ),

                )
        )


        val adapter = DashboardMenuAdaptor(requireContext(), DashBoardContainList, this)
        // Fill Recycler View
        val linearLayoutManager2 = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        val mLayoutManager: GridLayoutManager = GridLayoutManager(requireContext(), 2)
        val recyclerView = root.findViewById(R.id.recyclerViewDashboard) as RecyclerView
        recyclerView.layoutManager = mLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        return root
    }

    override fun onItemClick(position: Int) {
        if (DashBoardContainList[position].intentID == "Suggestion") {
            val intent = Intent(requireView().context, AboutHeSheActivity::class.java)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID ==  "Donate") {
            val intent = Intent(requireView().context, DonationActivity::class.java)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "Index") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/Index"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "ContactUs") {
            val intent = Intent(requireView().context, ContactUs::class.java)
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
        if (DashBoardContainList[position].intentID == "Approach") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/Approach"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "OrgStruct") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/OrgStruct"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "ThematicAreas") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/ThematicAreas"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "Geography") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/Geography"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }

        if (DashBoardContainList[position].intentID == "MazaorMilestone") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/MazaorMileStone"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "Certifications") {
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            var url = "https://www.bhoomikavihar.in/HomeAndroid/Legal"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
        }



    }

    override fun onLongItemClick(position: Int) {

    }
}