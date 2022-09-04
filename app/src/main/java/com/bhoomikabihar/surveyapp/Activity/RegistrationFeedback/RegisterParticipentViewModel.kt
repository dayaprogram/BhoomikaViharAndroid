package com.bhoomikabihar.surveyapp.Activity.RegistrationFeedback

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bseb.crossword.RemoteDataRepository.RemoteRepository
import com.bhoomikabihar.surveyapp.CustomClass.SingleLiveEvent
import com.bhoomikabihar.surveyapp.Model.*

class RegisterParticipentViewModel(application: Application) : AndroidViewModel(application) {

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

    fun getDistrictList(): MutableList<SpinnerObj> {
        val dataList: MutableList<SpinnerObj> = ArrayList()
        dataList.add(SpinnerObj(-1, "--Select--", true))
        dataList.add(SpinnerObj(1, "संस्थागत भूमि मालिक हैं।", true))
        dataList.add(SpinnerObj(2, "पूर्व में संवैधानिक पद पर थे।", true))
        dataList.add(SpinnerObj(3, "वर्त्तमान में संवैधानिक पद पर हैं।", true))
        dataList.add(SpinnerObj(4, "केंद्र/राज्य/स्थानीय निकायों/PSU के कर्मी हैं।", true))
        dataList.add(
            SpinnerObj(
                5,
                "सेवानिवृत कर्मी हैं जिनका मासिक पेंशन 10000 से अधिक हैं।",
                true
            )
        )
        dataList.add(SpinnerObj(6, "आयकरदाता हैं।", true))
        dataList.add(SpinnerObj(7, "निबंधित पेशेवर हैं।", true))
        dataList.add(SpinnerObj(8, "NRI", true))
        dataList.add(SpinnerObj(9, "लाभुक बिहार राज्य का नहीं हैं।", true))
//        dataList.add(SpinnerObj(10, "भूमि स्वामित्व किसान के नाम पर नहीं है।", true))
        dataList.add(SpinnerObj(11, "परिवार का मुखिया भी PM-KISAN योजना का लाभ ले रहे हैं।", true))
        dataList.add(SpinnerObj(12, "किसान भूमिहीन हो गया है।(भूमि खरीद/बिक्री के कारण )", true))
        dataList.add(
            SpinnerObj(
                13,
                "किसान द्वारा जानकारी प्रदान की गयी भूमि का कृषि कार्य से अन्य कार्यों में उपयोग होने लगा है।",
                true
            )
        )
        dataList.add(SpinnerObj(14, "लाभुक का पता नहीं  लगाया जा सका।", true))
        dataList.add(SpinnerObj(15, "एक ही लाभार्थी का दो बार PM - KISAN  पर पंजीकरण है ।", true))
        dataList.add(SpinnerObj(16, "किसान स्वेच्छा से  PM - KISAN लाभ नहीं लेना चाहता है।", true))
        return dataList
    }

    init {
        _farmerFamilyList.value = ArrayList()
    }


}
