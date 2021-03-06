package com.bhoomikabihar.surveyapp.Activity.MainFragments.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bseb.crossword.ViewAdaptor.DashboardMenuAdaptor
import com.bhoomikabihar.surveyapp.Activity.ContactUs
import com.bhoomikabihar.surveyapp.Activity.PMKISANAnkshan.PMKISANAnksheanActivity
import com.bhoomikabihar.surveyapp.Activity.PMKisanRecovery.PMKisanListRecoveryActivity
import com.bhoomikabihar.surveyapp.Activity.PMKisanVerification.PMKisanListVerifyActivity
import com.bhoomikabihar.surveyapp.Activity.PMKisanVerification.PMKisanACVerificationListActivity
import com.bhoomikabihar.surveyapp.BuildConfig
import com.bhoomikabihar.surveyapp.Model.DashBoardContain
import com.bhoomikabihar.surveyapp.R
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

        if (userRole == "DAO") {
            DashBoardContainList.addAll(
                listOf(

//                    DashBoardContain(
//                        "PMKisanRecovery",
//                        "??????.??????. ??????????????? ??????????????????",
//                        "",
//                        R.drawable.bihar_logo_copy
//                    ),
                    DashBoardContain(
                        "FarmerCallList",
                        "?????????????????? ???????????????????????? ???????????? ????????????",
                        "",
                        R.drawable.approve
                    ),
                    DashBoardContain(
                        "PMKISANAnkshan",
                        "PM-??????????????? ????????????????????????",
                        "",
                        R.drawable.approve
                    ),
                    DashBoardContain(
                        "ContactUs",
                        "????????????????????? ???????????????",
                        "",
                        R.drawable.contact
                    ),
                    DashBoardContain(
                        "UpdateApp",
                        "Update Application",
                        "",
                        R.drawable.optimization
                    )
                )
            )
        } else if (userRole == "AC") {
            DashBoardContainList.addAll(
                listOf(
//                    DashBoardContain(
//                        "FarmerVerification",
//                        "????????????????????? ?????????????????????",
//                        "",
//                        R.drawable.bihar_logo_copy
//                    ),
//                    DashBoardContain(
//                        "InputSubsidy",
//                        "???????????? ??????????????? ??????????????????",
//                        "???????????? (2020 -21)",
//                        R.drawable.bihar_logo_copy
//                    ),
                    DashBoardContain(
                        "PMKisanACVerification",
                        "??????.??????.-??????????????? ?????????????????????",
                        "??????.??????.-??????????????? ?????? ????????? ??????????????? ?????? ?????????????????????",
                        R.drawable.approve
                    ),
                    DashBoardContain(
                        "PMKisanVerification",
                        "??????.??????.-??????????????? ??????????????? ?????????????????????",
                        "",
                        R.drawable.immigration
                    ),
//                    DashBoardContain(
//                        "SocialAuditVerification",
//                        "??????.??????.-??????????????? ????????????????????? ????????????????????????",
//                        "",
//                        R.drawable.immigration
//                    ),

//                    DashBoardContain(
//                        "PMKisanRecovery",
//                        "??????.??????. ??????????????? ??????????????????",
//                        "",
//                        R.drawable.bihar_logo_copy
//                    ),
                    DashBoardContain(
                        "ContactUs",
                        "????????????????????? ???????????????",
                        "",
                        R.drawable.contact
                    ),
                    DashBoardContain(
                        "UpdateApp",
                        "Update Application",
                        "",
                        R.drawable.optimization
                    )
                )
            )
        } else if (userRole == "CO" || userRole == "ADM(Revenue)") {
            DashBoardContainList.addAll(
                listOf(
                    DashBoardContain(
                        "PMKisanACVerification",
                        "??????.??????.-??????????????? ?????????????????????",
                        "??????.??????.-??????????????? ?????? ????????? ??????????????? ?????? ?????????????????????",
                        R.drawable.approve
                    ),
                    /*   DashBoardContain(
                           "PMKisanVerification",
                           "??????.??????.-??????????????? ??????????????? ?????????????????????",
                           "",
                           R.drawable.immigration
                       ),*/

                    DashBoardContain(
                        "ContactUs",
                        "????????????????????? ???????????????",
                        "",
                        R.drawable.contact
                    ),
                    DashBoardContain(
                        "UpdateApp",
                        "Update Application",
                        "",
                        R.drawable.optimization
                    )
                )
            )
        }



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

        if (DashBoardContainList[position].intentID == "PMKISANAnkshan") {
            val intent = Intent(requireView().context, PMKISANAnksheanActivity::class.java)
            requireView().context.startActivity(intent)
        }

        if (DashBoardContainList[position].intentID == "PMKisanVerification") {
            val intent = Intent(requireView().context, PMKisanListVerifyActivity::class.java)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "PMKisanACVerification") {
            val intent =
                Intent(requireView().context, PMKisanACVerificationListActivity::class.java)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "SocialAuditVerification") {
            val intent =
                Intent(requireView().context, PMKisanACVerificationListActivity::class.java)
            requireView().context.startActivity(intent)
        }


        if (DashBoardContainList[position].intentID == "PMKisanRecovery") {
            val intent = Intent(requireView().context, PMKisanListRecoveryActivity::class.java)
            requireView().context.startActivity(intent)
        }

        if (DashBoardContainList[position].intentID == "ContactUs") {
            val intent = Intent(requireView().context, ContactUs::class.java)
            requireView().context.startActivity(intent)
        }
        if (DashBoardContainList[position].intentID == "UpdateApp") {
            val viewIntent = Intent(
                "android.intent.action.VIEW",
                Uri.parse("https://play.google.com/store/apps/details?id=com.dbtagri.dbtagriverify")
            )
            startActivity(viewIntent)
        }
    }

    override fun onLongItemClick(position: Int) {

    }
}