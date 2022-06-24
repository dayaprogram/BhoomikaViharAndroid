package com.example.dbtagri.LocalDataRepository.Dao

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.bhoomikabihar.surveyapp.Model.FarmerDetails
import com.bhoomikabihar.surveyapp.Model.InputSubsidyFarmerDetailsRequest
import com.bhoomikabihar.surveyapp.Model.LandDetails

class LocalDataRepository(context: Context) {


    var db = LocalDatabase.getInstance(context)
    private var localDao: LocalDao? = db?.localDao()
    var liveInputSubsidyUnApprovedFarmerList: MutableLiveData<List<FarmerDetails>> =
        MutableLiveData()
    var liveInputSubsidyUnApprovedFarmerLand: MutableLiveData<List<LandDetails>> =
        MutableLiveData()

    val liveUnApprovedFarmerCount: MutableLiveData<Int> = MutableLiveData()
    val liveApprovedFarmerCount: MutableLiveData<Int> = MutableLiveData()
    val liveRejectedFarmerCount: MutableLiveData<Int> = MutableLiveData()
    val liveApplicationStatus: MutableLiveData<Int> = MutableLiveData()
    val liveUnUploadFarmerCount: MutableLiveData<Int> = MutableLiveData()
    val liveActionToUploadFarmerCount: MutableLiveData<Int> = MutableLiveData()
    val liveInputSUbsidyApproverFarmerToUpload: MutableLiveData<List<InputSubsidyFarmerDetailsRequest>> =
        MutableLiveData()

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    //  val allWords: LiveData<List<User>> = localDao.getAlphabetizedWords()
    companion object Factory {
        fun getInstance(context: Context): LocalDataRepository {
            var localRepository: LocalDataRepository =
                LocalDataRepository(context)
            return localRepository
        }
    }


    fun insertInputSubsidyUnapprovedFarmer(farmerLIst: List<InputSubsidyApplicationLocal>): Long {
        var result: Long = 0

        if (farmerLIst != null) {
            localDao?.insertAllInputSubsidyApplicationLocal(farmerLIst)!!
        }
        return result
    }


    fun insertInputSubsidyUnapprovedFarmerLand(farmerLandList: List<InputSubsidyLandLocal>): Long {
        var result: Long = 0

        if (farmerLandList != null) {
            localDao?.insertAllInputSubsidyLandLocal(farmerLandList)!!
        }
        return result
    }


    fun getInputSubsidyApprovedFarmerDetailsTop1(): InputSubsidyApplicationLocal? {
        val farmerAppRegDetails = localDao?.getInputSubsidyApprovedFarmerDetailsTop1()
        return farmerAppRegDetails;
    }


    fun getInputSubsidyApprovedFarmerCount() {
        localDao?.getInputSubsidyApprovedFarmerCount()!!
            .observeForever { result ->
                liveApprovedFarmerCount.value = result
            }
    }

    fun getInputSubActionToUploadCount() {
        localDao?.getInputSubActionToUploadCount()!!
            .observeForever { result ->
                liveActionToUploadFarmerCount.value = result
            }
    }

    fun getInputSubsidyRejectedFarmerCount() {
        localDao?.getInputSubsidyRejectFarmerCount()!!
            .observeForever { result ->
                liveRejectedFarmerCount.value = result
            }
    }

    fun getInputSubsidyUnApprovedFarmerCount() {
        localDao?.getInputSubsidyUnApprovedFarmerCount()!!
            .observeForever { result ->
                liveUnApprovedFarmerCount.value = result
            }
    }

    fun getInputSubUnUploadCount() {
        localDao?.getInputSubUnUploadCount()!!
            .observeForever { result ->
                liveUnUploadFarmerCount.value = result
            }
    }

    fun getInputSubsidyUnApprovedFarmerList() {
        localDao?.getInputSubsidyUnApprovedFarmerList()!!
            .observeForever { resultList ->

                var InputSubsidyMap: List<FarmerDetails> =
                    resultList.map {
                        FarmerDetails(
                            it.registrationNo, it.applicationID, it.applicantName!!,
                            it.fatherHusbandName!!, it.totalAffectedRakwa!!, it.totalSubsidy!!,
                            it.mobileNo!!, it.schemeType!!,
                            it.distCode!!, it.blockCode!!, it.panchayatCode!!
                        )
                    };
                liveInputSubsidyUnApprovedFarmerList.value = InputSubsidyMap
            }

    }


    fun getUnApprovedFarmerLand(regNo: String) {
        localDao?.getInputSubsidyUnApprovedFarmerLandDetails(regNo)!!
            .observeForever { resultList ->
                var InputSubsidyLandMap: List<LandDetails> =
                    resultList.map {
                        LandDetails(
                            it.registrationNo,
                            it.applicationID!!,
                            it.KisanType!!,
                            it.CropType!!,
                            it.LandType!!,
                            it.KhataNo!!,
                            it.ThanaNo!!,
                            it.KeshraNo!!,
                            it.Affectedrakwa!!,
                            it.AnudanAmount!!, 0.0, 0.0, 0, it.landId
                        )
                    };
                liveInputSubsidyUnApprovedFarmerLand.value = InputSubsidyLandMap
            }

    }

    fun updateInputSubsidyAction(
        aCStatus: Int?,
        aCRemarks: String,
        ACReason: String,
        RegistrationNo: String,
        ApplicationID: String,
        soiltest: String,
        photoLatitude: Double,
        photoLongitude: Double,
        IMEINumber: String
    ): Long {
        var result: Long = 0

        localDao!!.updateInputSubsidyAction(
            aCStatus,
            aCRemarks,
            ACReason,
            RegistrationNo,
            ApplicationID,
            soiltest,
            photoLatitude,
            photoLongitude,
            IMEINumber
        )
        return result
    }

    fun updateInputSubsidyActionRejected(
        aCStatus: Int?,
        aCRemarks: String,
        ACReason: String,
        RegistrationNo: String,
        ApplicationID: String,
        SoilTest: String,
        photoLatitude: Double,
        photoLongitude: Double,
        IMEINumber: String,
        ActionTakenDtTime: String
    ): Long {
        var result: Long = 0

        localDao!!.updateInputSubsidyActionRejected(
            aCStatus,
            aCRemarks,
            ACReason,
            RegistrationNo,
            ApplicationID,
            SoilTest,
            photoLatitude,
            photoLongitude,
            IMEINumber,
            ActionTakenDtTime
        )
        return result
    }


    fun updateInputSubsidyLandApproval(
        totalApprovedLand: Double,
        totalApprovedAmt: Double,
        RegistrationNo: String,
        ApplicationID: String
    ): Long {
        var result: Long = 0

        localDao!!.updateInputSubsidyLandApproval(
            totalApprovedLand,
            totalApprovedAmt,
            RegistrationNo,
            ApplicationID
        )
        return result
    }


    fun updateInputSubsidyPicturedetails(

        photoPath: String,
        PhotoTakenDtTime: String,
        RegistrationNo: String,
        ApplicationID: String
    ): Long {
        var result: Long = 0

        localDao!!.updateInputSubsidyPicturedetails(

            photoPath,
            PhotoTakenDtTime,
            RegistrationNo,
            ApplicationID
        )
        return result
    }

    fun updateInputSubsidyFarmerDetailsFinal(
        FarmerName1: String,
        FarmerMobile1: String,
        FarmerName2: String,
        FarmerMobile2: String,

        RegistrationNo: String,
        ApplicationID: String
    ): Long {
        var result: Long = 0

        localDao!!.updateInputSubsidyFarmerDetailsFinal(
            FarmerName1,
            FarmerMobile1,
            FarmerName2,
            FarmerMobile2,

            RegistrationNo,
            ApplicationID
        )
        return result
    }


    fun getInputSubsidyApplicationStatus(
        RegistrationNo: String,
        ApplicationID: String
    ) {
        localDao!!.getInputSubsidyApplicationStatus(
            RegistrationNo,
            ApplicationID
        ).observeForever { result -> liveApplicationStatus.value = result }

    }

    fun getInputSubsidyApprovedFarmerToUpload() {
        localDao?.getInputSubsidyApprovedFarmer()!!
            .observeForever { it ->
                var InputSubsidyMapReqList = it.map {
                    InputSubsidyFarmerDetailsRequest(
                        it.registrationNo,
                        it.applicationID,
                        it.registrationNo,
                        it.aCStatus!!,
                        it.aCRemarks!!,
                        it.ACReason!!,
                        it.totalApprovedLand!!,
                        it.totalApprovedAmt!!,
                        it.photoLatitude!!,
                        it.photoLongitude!!,
                        it.photoPath!!,
                        it.actionTakenDtTime!!,
                        it.FarmerName1!!,
                        it.FarmerMobile1!!,
                        it.FarmerName2!!,
                        it.FarmerMobile2!!,
                        it.SoilTest!!,
                        it.imeiNo
                    )
                }

                liveInputSUbsidyApproverFarmerToUpload.value = InputSubsidyMapReqList
                //  localDao!!.updateMarkInputSubsidyApprovedFarmer(it.registrationNo, it.applicationID)
            }
    }

    fun uploadMarkInputSubsidyApprovedFarmer(
        RegistrationNo: String,
        ApplicationID: String
    ) {
        localDao!!.uploadMarkInputSubsidyApprovedFarmer(
            RegistrationNo,
            ApplicationID
        )
    }

    fun deleteInputSubsidyData() {
        localDao!!.deleteInputSubsidyData()
    }


//        var DocumentFileName: String = "",

//        var ACStatus: Int = 0,
//        var ACRemarks: String = "",
//        var ACRejectReason: String = "",
//        var TotalApprovedLand: Double = 0.0,
//        var TotalApprovedAmt: Double = 0.0,
//        var PhotoLatitude: Double = 0.0,
//        var PhotoLongitude: Double = 0.0,
//        var PhotoPath: String = "",
//        var PhotoTakenDtTime: String = "",
//        var FarmerName1: String = "",
//        var FarmerMobile1: String = "",
//        var FarmerName2: String = "",
//        var FarmerMobile2: String = "",
//        var SoilTest: String = "",
//        var imeiNo: String? = ""


}
//
//    fun updateFarmerAppRegistration(farmerAppRegistration: FarmerAppRegistration) {
//
//        localDao?.updateFarmerAppRegistration(farmerAppRegistration)
//
//    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun insertFarmerRegistrationDetails(farmerRegistrationDetails: FarmerRegistrationDetails) {
//        val farmerRegDetails =
//            localDao?.getFarmerRegistrationDetailsLocal(farmerRegistrationDetails.registrationNo)
//
//        var regDetailsLocal =
//            this.getFarmerAppRegistrationDetails(farmerRegistrationDetails.registrationNo)
//
//        if (farmerRegistrationDetails.registrationNo == "") {
//
//            if (regDetailsLocal != null) {
//                farmerRegistrationDetails.registrationNo = regDetailsLocal.registrationNo
//            }
//        }
//
//        if (farmerRegDetails == null) {
//            localDao?.insertFarmerRegistrationDetailsLocal(
//                farmerRegistrationDetails
//            )
//        } else {
//
//
//            if (regDetailsLocal != null) {
////                regDetailsLocal.farmerName =
////                    (farmerRegistrationDetails.farmerFName + " " + farmerRegistrationDetails.farmerLName).trim()
////                regDetailsLocal.registrationFlag="COMPLETE"
////                regDetailsLocal.entryDate = LocalDateTime.now()
////                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
////                regDetailsLocal.appRegistrationDateTime = LocalDateTime.now()
////                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
////                this.insertFarmerAppRegistration(regDetailsLocal)
//
//                localDao?.updateFarmerRegistrationDetailsLocal(farmerRegistrationDetails)
//            }
//
//        }
//    }

//    fun getFarmerRegistrationDetails(): MutableLiveData<FarmerRegistrationDetails>? {
//        var farmerAppRegDetailsResult: MutableLiveData<FarmerRegistrationDetails>? = null
//        val farmerAppRegDetails = localDao?.getFarmerAppRegistrationLocal()
//        if (farmerAppRegDetails != null) {
//
//            if (farmerAppRegDetailsResult != null) {
//                farmerAppRegDetailsResult.value =
//                    localDao?.getFarmerRegistraionDetailsLocal(farmerAppRegDetails.registrationNo)
//            }
//
//
//        }
//        return farmerAppRegDetailsResult
//    }

//    fun getFarmerRegistrationDetails(): FarmerRegistrationDetails? {
//
//        val farmerRegDetails = localDao?.getFarmerRegistrationDetailsLocal()
////        if (farmerRegDetails != null) {
////
////            farmerAppRegDetailsResult =
////                localDao?.getFarmerRegistrationDetailsLocal(farmerRegDetails.registrationNo)!!
////
////        }
//        return farmerRegDetails
//    }


