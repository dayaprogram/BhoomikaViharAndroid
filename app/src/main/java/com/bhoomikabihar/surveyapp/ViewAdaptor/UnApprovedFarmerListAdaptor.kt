package com.bseb.crossword.ViewAdaptor


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhoomikabihar.surveyapp.Model.FarmerDetails
import com.bhoomikabihar.surveyapp.R
import com.example.dbtagri.ViewAdaptor.RecyclerViewClickInterfaceDataCall


public class UnApprovedFarmerListAdaptor(
    mCtx: Context,
    farmerList: List<FarmerDetails>,
    recyclerViewClickInterface: RecyclerViewClickInterfaceDataCall
) : RecyclerView.Adapter<UnApprovedFarmerListAdaptor.DashboardMenuViewHolder>() {
    //this context we will use to inflate the layout
    private val mCtx: Context

    //we are storing all the products in a list
    private var farmerList: List<FarmerDetails>
    private var recyclerViewClickInterface: RecyclerViewClickInterfaceDataCall


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardMenuViewHolder {
        //inflating and returning our view holder
        val inflater = LayoutInflater.from(mCtx)
        val view: View = inflater.inflate(R.layout.layout_farmer_list, null)
        return DashboardMenuViewHolder(view, this.recyclerViewClickInterface)
    }

    override fun onBindViewHolder(viewHolder: DashboardMenuViewHolder, position: Int) {
        //getting the product of the specified position
        val farmerDtl: FarmerDetails = farmerList[position]

        //binding the data with the viewholder views
        viewHolder.textRegNo.setText(farmerDtl.regId)
        viewHolder.textApplicationId.setText(farmerDtl.appId)
        viewHolder.textFName.setText(farmerDtl.applicantName)
        viewHolder.textFFatherName.setText(farmerDtl.father_Husband_Name)
        viewHolder.textFMobNo.setText("Mo.: " + farmerDtl.mobileNo)
        viewHolder.textAffectedLand.setText(farmerDtl.totalAffectedRakwa.toString())
        viewHolder.textSubsidyAmt.setText(farmerDtl.totalSubsidy.toString())

        viewHolder.viewsLayout.setVerticalGravity(position)
        if (position % 2 == 1) {
//            val param = viewHolder.viewsLayout.layoutParams as ViewGroup.MarginLayoutParams
//            val param1 = viewHolder.textViewTitle.layoutParams as ViewGroup.MarginLayoutParams
//            // param.setMargins(10, 30, 10, 10)
//            param1.setMargins(10, 8, 32, 48)
            viewHolder.viewsLayout.setBackgroundColor(Color.argb(100, 223, 252, 227))
            //        viewHolder.viewsLayout.setBackgroundColor(Color.argb(100, 243, 181, 181))
//            //viewHolder.viewsLayout.layoutParams = param
//            //viewHolder.viewsLayout.animation
//            viewHolder.textViewTitle.layoutParams = param1
////            viewHolder.textViewTitle.textSize = 24F
//            viewHolder.textViewTitle.setTypeface(null, Typeface.BOLD);
            //viewHolder.viewsLayout.setBackgroundColor(Color.argb(100, 251, 251, 226))
        } else {
            viewHolder.viewsLayout.setBackgroundColor(Color.argb(100, 251, 251, 226))
        }
    }

    override fun getItemCount(): Int {
        return farmerList.size
    }

    inner class DashboardMenuViewHolder(
        itemView: View,
        recyclerViewClickInterface: RecyclerViewClickInterfaceDataCall
    ) : RecyclerView.ViewHolder(itemView) {

        var textRegNo: TextView
        var textApplicationId: TextView
        var textFName: TextView
        var textFFatherName: TextView
        var textFMobNo: TextView
        var textAffectedLand: TextView
        var textSubsidyAmt: TextView
        var viewsLayout: RelativeLayout


        init {
            textRegNo = itemView.findViewById(R.id.textRegNo)
            textApplicationId = itemView.findViewById(R.id.textApplicationId)
            textFName = itemView.findViewById(R.id.textFName)
            textFFatherName = itemView.findViewById(R.id.textFFatherName)
            textFMobNo = itemView.findViewById(R.id.textFMobNo)
            textAffectedLand = itemView.findViewById(R.id.textAffectedLand)
            textSubsidyAmt = itemView.findViewById(R.id.textSubsidyAmt)
            viewsLayout = itemView.findViewById(R.id.farmerListLayout)
            itemView.setOnClickListener(View.OnClickListener {
                recyclerViewClickInterface.onItemClick(farmerList[adapterPosition])
            })
//            itemView.setOnLongClickListener(View.OnLongClickListener {
//                recyclerViewClickInterface.onLongItemClick(adapterPosition)
//                return@OnLongClickListener true
//            })
        }
    }

    //getting the context and product list with constructor
    init {
        this.mCtx = mCtx
        this.farmerList = farmerList
        this.recyclerViewClickInterface = recyclerViewClickInterface
    }
}