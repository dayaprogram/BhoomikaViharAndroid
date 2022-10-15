package com.bseb.crossword.ViewAdaptor


import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhoomikabihar.surveyapp.Model.DashBoardContain
import com.bhoomikabihar.surveyapp.R
import com.example.dbtagri.ViewAdaptor.RecyclerViewClickInterface


public class DashboardMenuAdaptor(
    mCtx: Context,
    dashBoardContainList: List<DashBoardContain>,
    recyclerViewClickInterface: RecyclerViewClickInterface
) : RecyclerView.Adapter<DashboardMenuAdaptor.DashboardMenuViewHolder>() {
    //this context we will use to inflate the layout
    private val mCtx: Context

    //we are storing all the products in a list
    private var dashBoardContainList: List<DashBoardContain>
    private var recyclerViewClickInterface: RecyclerViewClickInterface


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardMenuViewHolder {
        //inflating and returning our view holder
        val inflater = LayoutInflater.from(mCtx)
        val view: View = inflater.inflate(R.layout.layout_dashboard_menu, null)
        return DashboardMenuViewHolder(view, this.recyclerViewClickInterface)
    }

    override fun onBindViewHolder(viewHolder: DashboardMenuViewHolder, position: Int) {
        //getting the product of the specified position
        val dashBoardContain: DashBoardContain = dashBoardContainList[position]

        //binding the data with the viewholder views
        viewHolder.textViewTitle.setText(dashBoardContain.title)
//        viewHolder.textViewShortDesc.setText(dashBoardContaint.shortdesc)
        viewHolder.textViewShortDesc.setText(dashBoardContain.shortdesc)
        viewHolder.imageView.setImageDrawable(
            mCtx.resources.getDrawable(dashBoardContain.image)
        )
        viewHolder.viewsLayout.setVerticalGravity(position)
        val param1 = viewHolder.textViewTitle.layoutParams as ViewGroup.MarginLayoutParams
        viewHolder.textViewTitle.layoutParams = param1
//            viewHolder.textViewTitle.textSize = 24F
        viewHolder.textViewTitle.setTypeface(null, Typeface.BOLD);
//        if (position % 3 == 1) {
//            param1.setMargins(10, 8, 32, 48)
//            viewHolder.viewsLayout.setBackgroundColor(Color.argb(100, 243, 181, 181))
//        } else {
//            viewHolder.viewsLayout.setBackgroundColor(Color.argb(100, 251, 251, 226))
//        }
    }

    override fun getItemCount(): Int {
        return dashBoardContainList.size
    }

    inner class DashboardMenuViewHolder(
        itemView: View,
        recyclerViewClickInterface: RecyclerViewClickInterface
    ) : RecyclerView.ViewHolder(itemView) {

        var textViewTitle: TextView
        var textViewShortDesc: TextView
        var imageView: ImageView
        var viewsLayout: RelativeLayout


        init {
            textViewTitle = itemView.findViewById(R.id.textViewTitle)
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc)
            imageView = itemView.findViewById(R.id.imageView)
            viewsLayout = itemView.findViewById(R.id.dashboardLayout)
            itemView.setOnClickListener(View.OnClickListener {
                recyclerViewClickInterface.onItemClick(adapterPosition)
            })
            itemView.setOnLongClickListener(View.OnLongClickListener {
                recyclerViewClickInterface.onLongItemClick(adapterPosition)
                return@OnLongClickListener true
            })
        }
    }

    //getting the context and product list with constructor
    init {
        this.mCtx = mCtx
        this.dashBoardContainList = dashBoardContainList
        this.recyclerViewClickInterface = recyclerViewClickInterface
    }
}