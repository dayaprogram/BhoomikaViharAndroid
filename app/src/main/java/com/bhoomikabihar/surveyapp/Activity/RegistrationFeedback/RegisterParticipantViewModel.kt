package com.bhoomikabihar.surveyapp.Activity.PMKisanVerification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bseb.crossword.RemoteDataRepository.RemoteRepository
import com.bhoomikabihar.surveyapp.CustomClass.SingleLiveEvent
import com.bhoomikabihar.surveyapp.Model.*

class RegisterParticipantViewModel(application: Application) : AndroidViewModel(application) {

    private var remoteRepository: RemoteRepository? = null


    private var _unVerifiedFarmerList = MutableLiveData<List<FarmerRegDetails>>()
    val liveUnVerifiedFarmerList: LiveData<List<FarmerRegDetails>>
        get() = _unVerifiedFarmerList


    private var _unVerifiedFarmer = MutableLiveData<List<FarmerRegDetails>>()
    val liveUnVerifiedFarmer: LiveData<List<FarmerRegDetails>>
        get() = _unVerifiedFarmer


    var farmerFamily = SingleLiveEvent<FamilyRegDetails>()
//    val liveFarmerFamily: LiveData<FamilyRegDetails>
//        get() = _farmerFamily

    private var _farmerVerifyResponse = MutableLiveData<Int>()
    val liveFarmerVerifyResponse: LiveData<Int>
        get() = _farmerVerifyResponse

    private var _unVerifiedSelectedFarmer = MutableLiveData<FarmerRegDetails>()
    val liveUnApproveSelectedFarmer: LiveData<FarmerRegDetails>
        get() = _unVerifiedSelectedFarmer


    private var _farmerFamilyList = MutableLiveData<MutableList<FamilyRegDetails>>()
    val liveFarmerFamilyList: LiveData<MutableList<FamilyRegDetails>>
        get() = _farmerFamilyList


    init {
        remoteRepository = RemoteRepository.getInstance(application.applicationContext)
    }

    fun setUnVerifiedSelectedFarmer(farmer: FarmerRegDetails) {
        _unVerifiedSelectedFarmer.value = farmer
    }

    fun getUnVerifiedPMKisanList() {
        remoteRepository?.getUnVerifiedSocialAuditPMKisanFarmer()!!
        remoteRepository?.liveUnVerifiedSocialAuditFarmerList!!.observeForever { result ->
            _unVerifiedFarmerList.value = result
        }
    }

    fun getUnVerifiedFarmerByMobileNo(MobNo: String) {
        remoteRepository?.getUnVerifiedPMKisanFarmerByMobileNo(MobNo)!!
        remoteRepository?.liveUnVerifiedPMkisanFarmer!!.observeForever { result ->
            _unVerifiedFarmer.value = result
        }
    }

    fun saveApprovedFarmerDetails(farmerVerifyData: PMKisanSocialAuditModel) {
        remoteRepository?.saveVerifiedSocialAuditPMKisanFarmerDetails(farmerVerifyData)!!
        remoteRepository?.liveFarmerVerifiedPMKisanResponse!!.observeForever { result ->
            _farmerVerifyResponse.value = result
        }
    }


    init {
        _farmerFamilyList.value = ArrayList()
    }


}
