package com.example.dbtagri.ViewAdaptor

import com.bhoomikabihar.surveyapp.Model.FamilyRegDetails
import com.bhoomikabihar.surveyapp.Model.FarmerDetails
import com.bhoomikabihar.surveyapp.Model.FarmerRegDetails

interface RecyclerViewClickInterface {
    fun onItemClick(position: Int)
    fun onLongItemClick(position: Int)
}

interface RecyclerViewClickInterfaceDataCall {
    fun onItemClick(data: FarmerDetails)
    // fun onLongItemClick(data: FarmerDetails)
}

interface RecyclerViewClickInterfaceVerifyFarmer {
    fun onItemClick(data: FarmerRegDetails)

    // fun onLongItemClick(data: FarmerDetails)
}

interface RecyclerViewClickInterfaceFamilyList {
    //    fun onItemClick(data: FamilyRegDetails)
    fun onLongItemClick(data: FamilyRegDetails)
}