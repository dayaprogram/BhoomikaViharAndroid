package com.bhoomikabihar.surveyapp.Model.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bseb.crossword.RemoteDataRepository.RemoteRepository
import com.bhoomikabihar.surveyapp.Model.FarmerDetails
import com.bhoomikabihar.surveyapp.Model.InputSubsidyFarmerDetailsResponse
import com.bhoomikabihar.surveyapp.Model.LandDetails
import com.example.dbtagri.LocalDataRepository.Dao.InputSubsidyApplicationLocal
import com.example.dbtagri.LocalDataRepository.Dao.InputSubsidyLandLocal
import com.example.dbtagri.LocalDataRepository.Dao.LocalDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CommanViewModel(application: Application) : AndroidViewModel(application) {

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


    private var _farmerVerifyResponse = MutableLiveData<InputSubsidyFarmerDetailsResponse>()
    val liveFarmerVerifyResponse: LiveData<InputSubsidyFarmerDetailsResponse>
        get() = _farmerVerifyResponse


    private var _unApprovedFarmerCount = MutableLiveData<Int>()
    val liveUnApprovedFarmerCount: LiveData<Int>
        get() = _unApprovedFarmerCount


    private var _approvedFarmerCount = MutableLiveData<Int>()
    val liveApprovedFarmerCount: LiveData<Int>
        get() = _approvedFarmerCount

    private var _rejectFarmerCount = MutableLiveData<Int>()
    val liveRejectFarmerCount: LiveData<Int>
        get() = _rejectFarmerCount

    private var _syncProcessStatus = MutableLiveData<Int>()
    val liveSyncProcessStatus: LiveData<Int>
        get() = _syncProcessStatus

    private var _unUploaedFarmerCount = MutableLiveData<Int>()
    val liveUnUploaedFarmerCount: LiveData<Int>
        get() = _unUploaedFarmerCount

    init {
        remoteRepository = RemoteRepository.getInstance(application.applicationContext)
        localRepository = LocalDataRepository.getInstance(application.applicationContext)
    }


    fun getUnApprovedFarmerListSync() {

        remoteRepository?.getUnApprovedFarmersWithLandList()!!
        remoteRepository?.liveUnapprovedFarmersWithLands!!.observeForever { result ->
            if (result != null) {
                var InputSubsidyMap: List<InputSubsidyApplicationLocal> =
                    result.FarmerDetails.map {
                        InputSubsidyApplicationLocal(
                            it.regId, it.appId, it.applicantName,
                            it.father_Husband_Name, it.totalAffectedRakwa, it.totalSubsidy,
                            it.mobileNo, it.distcode, it.blockCode,
                            it.panchayatCode, it.schemeType, 0,
                            "", "", 0.0,
                            0.0, 0.0, 0.0,
                            "", ""
                        )
                    }

                var InputSubsidyLandMap: List<InputSubsidyLandLocal> = result.LandDetails.map {
                    InputSubsidyLandLocal(
                        it.frmRegID, it.frmAppId, it.kisanType, it.cropType, it.landType,
                        it.khatano, it.thanaNo, it.keshrano, it.affectedRakwa, it.anudanAmount
                    )
                }

                GlobalScope.launch(Dispatchers.IO) {
                    localRepository?.insertInputSubsidyUnapprovedFarmer(InputSubsidyMap)
                }
                GlobalScope.launch(Dispatchers.IO) {
                    localRepository?.insertInputSubsidyUnapprovedFarmerLand(InputSubsidyLandMap)
                }
                _syncProcessStatus.value = 1
            }
        }

    }

    fun getApprovedFarmerCount() {
        localRepository!!.getInputSubsidyApprovedFarmerCount()
        localRepository!!.liveApprovedFarmerCount.observeForever { result ->
            _approvedFarmerCount.value = result
        }
    }

    fun getRejectedFarmerCount() {
        localRepository!!.getInputSubsidyRejectedFarmerCount()
        localRepository!!.liveRejectedFarmerCount.observeForever { result ->
            _rejectFarmerCount.value = result
        }
    }

    fun getUnApprovedFarmerCount() {
        localRepository!!.getInputSubsidyUnApprovedFarmerCount()
        localRepository!!.liveUnApprovedFarmerCount.observeForever { result ->
            _unApprovedFarmerCount.value = result
        }
    }

    fun getInputSubUnUploadCount() {
        localRepository!!.getInputSubUnUploadCount()
        localRepository!!.liveUnUploadFarmerCount.observeForever { result ->
            _unUploaedFarmerCount.value = result
        }
    }

    fun saveInputSubsidyApprovedFarmerDetailsUpload() {
        localRepository?.getInputSubsidyApprovedFarmerToUpload()
        localRepository?.liveInputSUbsidyApproverFarmerToUpload!!.observeForever { results ->
            if (results != null) {
                // runBlocking {
                for (result in results) {
                    //      launch {
                    //      delay(1000)
                    remoteRepository?.saveApprovedFarmerDetails(result)!!
//                        }
//                    }
                }
            }
        }
        remoteRepository?.liveFarmerInputApproveUploadResponse!!.observeForever { result ->
            // if (result.uploadFlag == 1) {
            GlobalScope.launch(Dispatchers.IO) {
                localRepository?.uploadMarkInputSubsidyApprovedFarmer(
                    result.RegistrationNo,
                    result.ApplicationID
                )
            }
            // }
            _farmerVerifyResponse.value = result
        }
    }


}


