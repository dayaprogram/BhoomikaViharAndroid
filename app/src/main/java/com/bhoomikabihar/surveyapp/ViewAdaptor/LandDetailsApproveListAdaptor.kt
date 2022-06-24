package com.bhoomikabihar.surveyapp.ViewAdaptor


import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bhoomikabihar.surveyapp.Model.LandDetails
import com.bhoomikabihar.surveyapp.R
import java.math.RoundingMode
import java.text.DecimalFormat

open class LandDetailsApproveListAdaptor(
    ctx: Context?,
    editModelArrayListP: List<LandDetails>,
    recyclerViewEditTextInterface: RecyclerViewEditTextInterface
) :
    RecyclerView.Adapter<LandDetailsApproveListAdaptor.MyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(ctx)
    private val ctxl: Context = ctx!!
    private var editModelArrayList: List<LandDetails> = editModelArrayListP
    private var recyclerViewEditTextInterface: RecyclerViewEditTextInterface =
        recyclerViewEditTextInterface

//    private var viewModel: InputSubsidyViewModel = InputSubsidyViewModel(Application())

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        val view: View = inflater.inflate(R.layout.layout_land_detail_list, parent, false)
        return MyViewHolder(view, recyclerViewEditTextInterface)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val landDlt: LandDetails = editModelArrayList[position]
        holder.slNo.text = (position + 1).toString()
        if (landDlt.cropType == 1) {
            holder.textCropName.text = "शाश्वत फसल"
        } else if (landDlt.cropType == 2) {
            holder.textCropName.text = "धान"
        } else if (landDlt.cropType == 3) {
            holder.textCropName.text = "मक्का"
        } else if (landDlt.cropType == 4) {
            holder.textCropName.text = "अन्य फसल/सब्जी"
        }
        if (landDlt.landType == 1) {
            holder.textTypeofLand.text = "सिंचित"
            var actionNameList: List<String?> = listOf(
                "जमीन प्रकार चुनें",
                "सिंचित",
                "वर्षा आश्रित/असिंचित"
            )

            val dataAdapterAction =
                ArrayAdapter(ctxl, android.R.layout.simple_spinner_item, actionNameList)
            dataAdapterAction.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            if (holder.spinnerChngLandType != null) {
                holder.spinnerChngLandType.adapter = dataAdapterAction
            }
        } else if (landDlt.landType == 2) {
            holder.textTypeofLand.text = "वर्षा आश्रित/असिंचित"
            var actionNameList: List<String?> = listOf(
                "वर्षा आश्रित/असिंचित"
            )
            val dataAdapterAction =
                ArrayAdapter(ctxl, android.R.layout.simple_spinner_item, actionNameList)
            dataAdapterAction.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            if (holder.spinnerChngLandType != null) {
                holder.spinnerChngLandType.adapter = dataAdapterAction
            }
        }
        holder.textThanaNo.text = landDlt.thanaNo
        holder.textKhataNo.text = landDlt.khatano
        holder.textPlotNo.text = landDlt.keshrano
        holder.textAffctedLand.text = landDlt.affectedRakwa.toString()
        holder.textAspctedSubsidy.text = landDlt.anudanAmount.toString()
        holder.approvedAmt.text = "0.00"
    }

    override fun getItemCount(): Int {
        return editModelArrayList.size
    }

    inner class MyViewHolder(
        itemView: View,
        recyclerViewEditTextInterface: RecyclerViewEditTextInterface
    ) : RecyclerView.ViewHolder(itemView) {
        var slNo: TextView = itemView.findViewById(R.id.slNo)
        var textCropName: TextView = itemView.findViewById(R.id.textCropName)
        var textTypeofLand: TextView = itemView.findViewById(R.id.textTypeofLand)
        var textThanaNo: TextView = itemView.findViewById(R.id.textThanaNo)
        var textKhataNo: TextView = itemView.findViewById(R.id.textKhataNo)
        var textPlotNo: TextView = itemView.findViewById(R.id.textPlotNo)
        var textAffctedLand: TextView = itemView.findViewById(R.id.textAffctedLand)
        var textAspctedSubsidy: TextView = itemView.findViewById(R.id.textAspctedSubsidy)
        var approvedAmt: TextView = itemView.findViewById(R.id.approvedAmt)
        var landTypeAprved = 0
        var cropType = 0
        var approvedLand = 0.0

        var editTextApprovedLand =
            itemView.findViewById<View>(R.id.editTextApprovedLand) as EditText
        var spinnerChngLandType =
            itemView.findViewById<View>(R.id.spinnerChngLandType) as Spinner

        init {
            if (textCropName.text == "शाश्वत फसल") {
                cropType = 1
            } else if (textCropName.text == "धान") {
                cropType = 2
            } else if (textCropName.text == "मक्का") {
                cropType = 3
            } else if (textCropName.text == "अन्य फसल/सब्जी") {
                cropType = 4
            }
            editTextApprovedLand.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    charSequence: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    recyclerViewEditTextInterface.onTextChanged(
                        adapterPosition,
                        editTextApprovedLand.text.toString()
                    )


                    if (editTextApprovedLand.text.toString() == "") {
                        approvedLand = 0.0
                    } else
                        if (approvedLand > textAffctedLand.text.toString().toDouble()) {
                            editTextApprovedLand.text.clear()
                            approvedLand = 0.0
                            approvedAmt.text = 0.0.toString()
                        } else {
                            approvedLand = editTextApprovedLand.text.toString().toDouble()
                            approvedAmt.text = Subsidyamnt(
                                landTypeAprved,
                                cropType,
                                approvedLand
                            ).toString()
                        }
                }

                override fun afterTextChanged(editable: Editable) {}
            })
            spinnerChngLandType.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        var text = spinnerChngLandType.selectedItem.toString().trim()
                        recyclerViewEditTextInterface.onItemSelected(
                            adapterPosition, text
                        )

                        if (text == "सिंचित") {
                            landTypeAprved = 1
                        } else if (text == "वर्षा आश्रित/असिंचित") {
                            landTypeAprved = 2
                        }
                        approvedAmt.text =
                            Subsidyamnt(
                                landTypeAprved,
                                cropType,
                                approvedLand
                            ).toString()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }
        }
    }

    fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
    }

    fun Subsidyamnt(LandType: Int, CropType: Int, EffectedLand: Double): Double {
        var totalamnt: Double
        var CalcultatedAmount: Double
        var noofWatring: Int = 1
        // Saswat Fasal
        var ssAmount: Double = 72.81;
        //For all Fasal
        var allAmount: Double = 54.63;
        var RainallAmount: Double = 27.51;
        var Subsidyamnt: Double = 0.0;
        var totalland = EffectedLand;
        if (totalland > 494.00) {
            totalland = 494.20;
        }
        //var noofWatring:Int = abc.toInt()

//        if (DropDownList3.SelectedValue == "3") {
//            string GDAmount = "49.40";
//            Subsidyamnt = (totalland * double.Parse(GDAmount)) * noofWatring;
//        } else {
        if (LandType == 0) {
            Subsidyamnt = 0.0
        } else if (LandType == 1) {
            if (CropType == 1) {
                Subsidyamnt = (totalland.times(ssAmount)) * noofWatring;
            } else {
                Subsidyamnt = (totalland.times((allAmount)) * noofWatring);
            }
        } else if (LandType == 2) {
            if (CropType == 1) {
                Subsidyamnt = (totalland.times((ssAmount)) * noofWatring)
            } else {
                Subsidyamnt = (totalland.times((RainallAmount)) * noofWatring)
            }
        }
        // }
        totalamnt = Subsidyamnt
        var amount = totalamnt;
        if (amount < 1000.00) {
            CalcultatedAmount = totalamnt; //1000.00;
        } else {
            CalcultatedAmount = totalamnt;
        }
        return roundOffDecimal(CalcultatedAmount)
    }

}