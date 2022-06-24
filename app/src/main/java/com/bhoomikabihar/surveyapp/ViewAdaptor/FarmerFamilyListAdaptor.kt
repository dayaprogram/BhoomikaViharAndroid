package com.bseb.crossword.ViewAdaptor


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhoomikabihar.surveyapp.Model.FamilyRegDetails
import com.bhoomikabihar.surveyapp.R
import com.example.dbtagri.ViewAdaptor.RecyclerViewClickInterfaceFamilyList


public class FarmerFamilyListAdaptor(
    mCtx: Context,
    farmerList: List<FamilyRegDetails>,
    recyclerViewClickInterface: RecyclerViewClickInterfaceFamilyList
) : RecyclerView.Adapter<FarmerFamilyListAdaptor.DashboardMenuViewHolder>() {
    //this context we will use to inflate the layout
    private val mCtx: Context = mCtx

    //we are storing all the products in a list
    private var farmerList: List<FamilyRegDetails> = farmerList
    private var recyclerViewClickInterface: RecyclerViewClickInterfaceFamilyList =
        recyclerViewClickInterface


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardMenuViewHolder {
        //inflating and returning our view holder
        val inflater = LayoutInflater.from(mCtx)
        val view: View = inflater.inflate(R.layout.layout_farmer_family, null)
        return DashboardMenuViewHolder(view, this.recyclerViewClickInterface)
    }

    override fun onBindViewHolder(viewHolder: DashboardMenuViewHolder, position: Int) {
        //getting the product of the specified position
        val farmerDtl: FamilyRegDetails = farmerList[position]
        viewHolder.textSlno.text = (position + 1).toString() + ". "
        viewHolder.textRegNo.text = farmerDtl.Registration_ID
        viewHolder.textFName.text = (farmerDtl.Farmer_FName + " " + farmerDtl.Farmer_LName).trim()
        viewHolder.textFFamilyHName.text =
            ("F/H Name: " + farmerDtl.Father_Husband_Name).trim()
        viewHolder.textFFamilyVill.text =
            ("Vill: " + farmerDtl.VillName + " (" + farmerDtl.VillageCode + ")").trim()
    }

    override fun getItemCount(): Int {
        return farmerList.size
    }

    inner class DashboardMenuViewHolder(
        itemView: View,
        recyclerViewClickInterface: RecyclerViewClickInterfaceFamilyList
    ) : RecyclerView.ViewHolder(itemView) {

        var textSlno: TextView = itemView.findViewById(R.id.textSlno)
        var textFName: TextView = itemView.findViewById(R.id.textFFamiltName)
        var textRegNo: TextView = itemView.findViewById(R.id.textFFamilyRegNo)
        var textFFamilyVill: TextView = itemView.findViewById(R.id.textFFamilyVill)
        var textFFamilyHName: TextView = itemView.findViewById(R.id.textFFamilyHName)

        init {

            itemView.setOnClickListener(View.OnClickListener {
                recyclerViewClickInterface.onLongItemClick(farmerList[adapterPosition])
            })
        }
    }

}