package com.bhoomikabihar.surveyapp.Activity.PMKisanVerification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bseb.crossword.RemoteDataRepository.RemoteRepository
import com.bhoomikabihar.surveyapp.Model.*

class PMKISANAnkshenViewModel(application: Application) : AndroidViewModel(application) {

    private var remoteRepository: RemoteRepository? = null


    private var _farmerVerifyResponse = MutableLiveData<Int>()
    val liveFarmerVerifyResponse: LiveData<Int>
        get() = _farmerVerifyResponse


    private var _pmkisanAnkshanDataCount = MutableLiveData<PMKISANAnkshenModel>()
    val livepmkisanAnkshanDataCount: LiveData<PMKISANAnkshenModel>
        get() = _pmkisanAnkshanDataCount


    init {
        remoteRepository = RemoteRepository.getInstance(application.applicationContext)
    }


    fun GetCountPMKISANAnkshen() {
        remoteRepository?.GetCountPMKISANAnkshen()!!
        remoteRepository?.livePMKISANAnkshanDistCount!!.observeForever { result ->
            _pmkisanAnkshanDataCount.value = result
        }
    }


    fun savePMKISANAnkshenDetails(farmerVerifyData: PMKISANAnkshenModel) {
        remoteRepository?.savePMKISANAnkshenDetails(farmerVerifyData)!!
        remoteRepository?.livePMKISANAnkshanResponse!!.observeForever { result ->
            _farmerVerifyResponse.value = result
        }
    }


}
