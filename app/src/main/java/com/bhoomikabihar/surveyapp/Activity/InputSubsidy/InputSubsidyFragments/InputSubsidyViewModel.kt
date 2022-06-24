package com.bhoomikabihar.surveyapp.Activity.InputSubsidy.InputSubsidyFragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bseb.crossword.RemoteDataRepository.RemoteRepository
import com.bhoomikabihar.surveyapp.Model.FarmerDetails
import com.bhoomikabihar.surveyapp.Model.InputSubsidyFarmerDetailsRequest
import com.bhoomikabihar.surveyapp.Model.LandDetails
import com.example.dbtagri.LocalDataRepository.Dao.LocalDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

class InputSubsidyViewModel(application: Application) : AndroidViewModel(application) {

    private var remoteRepository: RemoteRepository? = null
    private var localRepository: LocalDataRepository? = null

    private var _unApproveFarmerList = MutableLiveData<List<FarmerDetails>>()
    val liveUnApproveFarmerList: LiveData<List<FarmerDetails>>
        get() = _unApproveFarmerList


    private var _unApproveFarmerLandList = MutableLiveData<List<LandDetails>>()
    val liveUnApproveFarmerLandList: LiveData<List<LandDetails>>
        get() = _unApproveFarmerLandList


    private var _unApproveSelectedFarmer = MutableLiveData<FarmerDetails>()
    val liveUnApproveSelectedFarmer: LiveData<FarmerDetails>
        get() = _unApproveSelectedFarmer

    private var _calculetedSubsidyAmt = MutableLiveData<Double>()
    val liveCalculetedSubsidyAmt: LiveData<Double>
        get() = _calculetedSubsidyAmt


    private var _calculetedApprovedLand = MutableLiveData<Double>()
    val liveCalculetedApprovedLand: LiveData<Double>
        get() = _calculetedApprovedLand

    private var _applicationStutus = MutableLiveData<Int>()
    val liveApplicationStutus: LiveData<Int>
        get() = _applicationStutus

    private var _inputSubcidyApprovedDetails =
        MutableLiveData<InputSubsidyFarmerDetailsRequest>().apply {
            postValue(InputSubsidyFarmerDetailsRequest())
        }

    val liveInputSubcidyApprovedDetails: LiveData<InputSubsidyFarmerDetailsRequest>
        get() = _inputSubcidyApprovedDetails

    init {
        remoteRepository = RemoteRepository.getInstance(application.applicationContext)
        localRepository = LocalDataRepository.getInstance(application.applicationContext)
    }


    fun getUnApprovedFarmerList() {
//        remoteRepository?.getUnApprovedFarmer()!!
//        remoteRepository?.liveUnapprovedFarmerResponse!!.observeForever { result ->
//            _unApproveFarmerList.value = result
//        }
        localRepository!!.getInputSubsidyUnApprovedFarmerList()
        localRepository!!.liveInputSubsidyUnApprovedFarmerList!!.observeForever { result ->
            _unApproveFarmerList.value = result
        }
    }

    fun getUnApprovedFarmerLand(regNo: String) {
//        remoteRepository?.getUnApprovedFarmerLand(regNo)!!
//        remoteRepository?.liveUnapprovedFarmerLand!!.observeForever { result ->
//            _unApproveFarmerLandList.value = result
//        }

        localRepository!!.getUnApprovedFarmerLand(regNo)
        localRepository!!.liveInputSubsidyUnApprovedFarmerLand!!.observeForever { result ->
            _unApproveFarmerLandList.value = result
        }
    }

    fun setUnApprovedSelectedFarmer(farmer: FarmerDetails) {
        _unApproveSelectedFarmer.value = farmer
    }

    fun setActionOnLand(landDetails: LandDetails) {
        _unApproveFarmerLandList.value?.find { landDetail -> landDetail.landId == landDetails.landId }?.approvedAmt =
            this.Subsidyamnt(
                landDetails.approvedLandType,
                landDetails.cropType,
                landDetails.approvedRakwa
            )
        val TotalSubsidyAmt: Double =
            _unApproveFarmerLandList.value!!.sumByDouble { it.approvedAmt }
        val TotalApprovedLand: Double =
            _unApproveFarmerLandList.value!!.sumByDouble { it.approvedRakwa }
        _calculetedSubsidyAmt.value = roundOffDecimal(TotalSubsidyAmt)
        _calculetedApprovedLand.value = roundOffDecimal(TotalApprovedLand)
    }


    fun setApplicationAction(
        ActionCode: Int,
        ReasonCode: Int,
        ACRemarks: String,
        regNo: String,
        AppNo: String,
        soiltest: String,
        PhotoLatitude: Double,
        PhotoLongitude: Double,
        IMEINumber: String
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            localRepository!!.updateInputSubsidyAction(
                ActionCode,
                ACRemarks,
                ReasonCode.toString(),
                regNo,
                AppNo,
                soiltest,
                PhotoLatitude,
                PhotoLongitude,
                IMEINumber
            )
        }
    }

    fun setApplicationActionReject(
        ActionCode: Int,
        ReasonCode: String,
        ACRemarks: String,
        regNo: String,
        AppNo: String,
        soiltest: String,
        PhotoLatitude: Double,
        PhotoLongitude: Double,
        IMEINumber: String,
        ActionTakenDtTime: String
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            localRepository!!.updateInputSubsidyActionRejected(
                ActionCode,
                ACRemarks,
                ReasonCode.toString(),
                regNo,
                AppNo, soiltest,
                PhotoLatitude,
                PhotoLongitude,
                IMEINumber,
                ActionTakenDtTime
            )
        }
    }


    fun setLandDetails(
        totalApprovedLand: Double,
        totalApprovedAmt: Double,
        regNo: String,
        AppNo: String
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            localRepository!!.updateInputSubsidyLandApproval(
                totalApprovedLand,
                totalApprovedAmt,
                regNo,
                AppNo
            )
        }
    }


    fun setPictureDetails(
        PhotoName: String,
        PhotoTakenDtTime: String,
        regNo: String,
        AppNo: String
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            localRepository!!.updateInputSubsidyPicturedetails(
                PhotoName,
                PhotoTakenDtTime,
                regNo,
                AppNo
            )
        }
    }

    fun setNighbourFarmerDetails(
        FarmerName1: String,
        FarmerMobile1: String,
        FarmerName2: String,
        FarmerMobile2: String,

        regNo: String,
        AppNo: String
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            localRepository!!.updateInputSubsidyFarmerDetailsFinal(
                FarmerName1,
                FarmerMobile1,
                FarmerName2,
                FarmerMobile2,

                regNo,
                AppNo
            )
        }
    }


    fun getInputSubsidyApplicationStatus(
        regNo: String,
        AppNo: String
    ) {
        localRepository!!.getInputSubsidyApplicationStatus(
            regNo,
            AppNo
        )
        localRepository!!.liveApplicationStatus!!.observeForever { result ->
            _applicationStutus.value = result
        }
    }

    private fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
    }

    fun Subsidyamnt(LandType: Int, CropType: Int, EffectedLand: Double): Double {
        var totalamnt: Double = 0.0;
        var CalcultatedAmount: Double = 0.0;
        var noofWatring: Int = 1
        // Saswat Fasal
        var ssAmount: Double = 72.81;
        //For all Fasal
        var allAmount: Double = 54.63;
        var RainallAmount: Double = 27.51;
        var Subsidyamnt: Double = 0.0;
        var totalland: Double = EffectedLand;
        if (totalland > "494.0".toDouble()) {
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
