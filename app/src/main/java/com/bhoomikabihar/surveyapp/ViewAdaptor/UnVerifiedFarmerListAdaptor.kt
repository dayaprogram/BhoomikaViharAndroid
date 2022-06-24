package com.bseb.crossword.ViewAdaptor


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhoomikabihar.surveyapp.Model.FarmerRegDetails
import com.bhoomikabihar.surveyapp.R
import com.example.dbtagri.ViewAdaptor.RecyclerViewClickInterfaceVerifyFarmer


public class UnVerifiedFarmerListAdaptor(
    mCtx: Context,
    farmerList: List<FarmerRegDetails>,
    recyclerViewClickInterface: RecyclerViewClickInterfaceVerifyFarmer
) : RecyclerView.Adapter<UnVerifiedFarmerListAdaptor.DashboardMenuViewHolder>(), Filterable {
    //this context we will use to inflate the layout
    private val mCtx: Context = mCtx

    private var farmerFilterList: List<FarmerRegDetails> = farmerList
    private var farmerAllList: List<FarmerRegDetails> = farmerList
    private var recyclerViewClickInterface: RecyclerViewClickInterfaceVerifyFarmer =
        recyclerViewClickInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardMenuViewHolder {
        //inflating and returning our view holder
        val inflater = LayoutInflater.from(mCtx)
        val view: View = inflater.inflate(R.layout.layout_farmer_list_verify, null)
        return DashboardMenuViewHolder(view, this.recyclerViewClickInterface)
    }

    override fun onBindViewHolder(viewHolder: DashboardMenuViewHolder, position: Int) {
        val farmerDtl: FarmerRegDetails = farmerFilterList[position]

        viewHolder.textRegNo.setText(farmerDtl.Registration_ID)
        viewHolder.textAadharNo.setText(farmerDtl.AadhaarNumber)
        viewHolder.textFName.setText(farmerDtl.Farmer_FName + farmerDtl.Farmer_LName)
        viewHolder.textFMobNo.setText(farmerDtl.MobileNumber)
        viewHolder.textDob.setText(farmerDtl.DOB)
        viewHolder.textGender.setText(farmerDtl.Gender)
        viewHolder.textVill.setText(farmerDtl.VillName)
        viewHolder.textCatogery.setText(farmerDtl.CastCateogary)
        viewHolder.textFFatherName.setText(farmerDtl.Father_Husband_Name)
        viewHolder.textFarmerGrade.setText(farmerDtl.FarmerGrade)
        viewHolder.viewsLayout.setVerticalGravity(position)
        if (position % 2 == 1) {
            viewHolder.viewsLayout.setBackgroundColor(Color.argb(100, 223, 252, 227))
        } else {
            viewHolder.viewsLayout.setBackgroundColor(Color.argb(100, 251, 251, 226))
        }
    }

    override fun getItemCount(): Int {
        return farmerFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterResults.values = farmerAllList
                } else {
                    val resultList = ArrayList<FarmerRegDetails>()
                    for (row in farmerAllList) {
                        if (row.Registration_ID.contains(charSearch) || row.Farmer_FName.toLowerCase()
                                .contains(
                                    charSearch.toLowerCase()
                                ) || row.AadhaarNumber.contains(charSearch) || row.MobileNumber.contains(
                                charSearch
                            )
                        ) {
                            resultList.add(row)
                        }
                    }
                    farmerFilterList = resultList
                    filterResults.values = farmerFilterList
                }
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                farmerFilterList = results?.values as ArrayList<FarmerRegDetails>
                notifyDataSetChanged()
            }

        }
    }

    inner class DashboardMenuViewHolder(
        itemView: View,
        recyclerViewClickInterface: RecyclerViewClickInterfaceVerifyFarmer
    ) : RecyclerView.ViewHolder(itemView) {

        var textRegNo: TextView
        var textAadharNo: TextView
        var textFName: TextView
        var textFMobNo: TextView
        var textDob: TextView
        var textGender: TextView
        var textVill: TextView
        var textCatogery: TextView
        var textFFatherName: TextView
        var textFarmerGrade: TextView
        var viewsLayout: RelativeLayout


        init {
            textRegNo = itemView.findViewById(R.id.textRegNo)
            textAadharNo = itemView.findViewById(R.id.textAadharNo)
            textFName = itemView.findViewById(R.id.textFName)
            textFMobNo = itemView.findViewById(R.id.textFMobNo)
            textDob = itemView.findViewById(R.id.textDob)
            textGender = itemView.findViewById(R.id.textGender)
            textVill = itemView.findViewById(R.id.textVill)
            textCatogery = itemView.findViewById(R.id.textCatogery)
            textFFatherName = itemView.findViewById(R.id.textFFatherName)
            textFarmerGrade = itemView.findViewById(R.id.textFarmerGrade)
            viewsLayout = itemView.findViewById(R.id.farmerVerifyListLayout)

            itemView.setOnClickListener(View.OnClickListener {
                recyclerViewClickInterface.onItemClick(farmerFilterList[adapterPosition])
            })

        }
    }

}