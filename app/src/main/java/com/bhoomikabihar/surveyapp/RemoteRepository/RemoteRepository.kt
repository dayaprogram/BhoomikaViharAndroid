package com.bseb.crossword.RemoteDataRepository

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bhoomikabihar.surveyapp.Comman.General
import com.bhoomikabihar.surveyapp.CustomClass.SingleLiveEvent
import com.bhoomikabihar.surveyapp.Model.*
import com.example.dbtagri.RemoteDataRepository.ApiClient
import com.example.dbtagri.RemoteDataRepository.SessionManager
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RemoteRepository(context: Context) {
    private lateinit var sessionManager: SessionManager
    val context = context
    var liveUnapprovedFarmerResponse: MutableLiveData<List<FarmerDetails>> = MutableLiveData()
    var liveUnapprovedFarmerLand: MutableLiveData<List<LandDetails>> = MutableLiveData()
    var liveUnapprovedFarmersWithLands: MutableLiveData<FarmerDetailsWithLand> = MutableLiveData()

    var liveUnVerifiedFarmerList: MutableLiveData<List<FarmerRegDetails>> = MutableLiveData()
    var liveUnVerifiedFarmer: MutableLiveData<FarmerRegDetails> = MutableLiveData()
    var liveUnVerifiedPMkisanFarmer: MutableLiveData<List<FarmerRegDetails>> = MutableLiveData()
    var liveFarmerFamily = SingleLiveEvent<FamilyRegDetails>()
    var liveFarmerVerifyResponse: MutableLiveData<Int> = MutableLiveData()
    var liveFarmerInputApproveUploadResponse: MutableLiveData<InputSubsidyFarmerDetailsResponse> =
        MutableLiveData()
    var liveAcLogoutResponse: MutableLiveData<Int> = MutableLiveData()

    var liveFarmerVerifiedPMKisanResponse: MutableLiveData<Int> = MutableLiveData()
    var liveFarmerCallVerifiedResponse: MutableLiveData<Int> = MutableLiveData()

    var liveUnRecoveredFarmerList: MutableLiveData<List<FarmerRegDetails>> = MutableLiveData()
    var liveFarmerRecoverdPMKisanResponse: MutableLiveData<Int> = MutableLiveData()

    var liveFarmerCallList: MutableLiveData<List<FarmerRegDetails>> = MutableLiveData()
    var liveDistrictList: MutableLiveData<List<DropDownDataset>> = MutableLiveData()
    var liveBlockList: MutableLiveData<List<DropDownDataset>> = MutableLiveData()
    var livePanchayatList: MutableLiveData<List<DropDownDataset>> = MutableLiveData()
    var liveVillageList: MutableLiveData<List<DropDownDataset>> = MutableLiveData()

    //------Social Audit-----Start--------//

    var liveUnVerifiedSocialAuditFarmerList: MutableLiveData<List<FarmerRegDetails>> =
        MutableLiveData()
    var liveFarmerVerifiedSocialAuditPMKisanResponse: MutableLiveData<Int> = MutableLiveData()

    //------Social Audit-----End--------//
//------CO Verification-----Start--------//
    var liveUnVerifiedCOAuditFarmerList: MutableLiveData<List<FarmerRegDetails>> =
        MutableLiveData()
    //------CO Verification-----End--------//

    // ------ADM Verification-----Start--------//
    var liveUnVerifiedADMAuditFarmerList: MutableLiveData<List<FarmerRegDetails>> =
        MutableLiveData()
    //------ADM Verification-----End--------//

    //------PMKISANAnkshan-----Start--------//

    var livePMKISANAnkshanDistCount: MutableLiveData<PMKISANAnkshenModel> =
        MutableLiveData()
    var livePMKISANAnkshanResponse: MutableLiveData<Int> = MutableLiveData()

    //------PMKISANAnkshan-----End--------//

    companion object Factory {
        // private lateinit var apiClient: ApiClient
        val httpClient = OkHttpClient.Builder()
        fun create(context: Context): ApiService {
            Log.e("retrofit", "create")
            return ApiClient().getApiService(context)
        }

        fun getInstance(context: Context): RemoteRepository {
            return RemoteRepository(context)
        }
    }


    fun getUnApprovedFarmer() {
        val retrofitCall = create(context).getUnApprovedFarmer()
        retrofitCall.enqueue(object : Callback<List<FarmerDetails>> {
            override fun onFailure(call: Call<List<FarmerDetails>>, t: Throwable) {
                Log.e("getUnApprovedFarmer", "Error")
            }

            override fun onResponse(
                call: Call<List<FarmerDetails>>,
                response: Response<List<FarmerDetails>>
            ) {
                if (response.isSuccessful)
                    liveUnapprovedFarmerResponse.value = response.body()
                Log.i("getUnApprovedFarmer", "GetUnApprovedFarmerSuccess")
            }
        })
    }

    fun getUnApprovedFarmerLand(regNo: String) {
        Log.e("getUnApprovedFarmerLand", "start")
        val retrofitCall = create(context).getUnApprovedFarmerLand(regNo)
        retrofitCall.enqueue(object : Callback<List<LandDetails>> {
            override fun onFailure(call: Call<List<LandDetails>>, t: Throwable) {
                Log.e("getUnApprovedFarmerLand", "Error")
            }

            override fun onResponse(
                call: Call<List<LandDetails>>,
                response: Response<List<LandDetails>>
            ) {
                if (response.isSuccessful)
                    liveUnapprovedFarmerLand.value = response.body()
                Log.i("getUnApprovedFarmerLand", "GetUnApprovedFarmerSuccess")
            }
        })
    }


    fun getUnApprovedFarmersWithLandList() {
        Log.e("getUnApprovedFarmerLand", "start")
        val retrofitCall = create(context).getUnApprovedFarmersAndLandList()
        retrofitCall.enqueue(object : Callback<FarmerDetailsWithLand> {
            override fun onFailure(call: Call<FarmerDetailsWithLand>, t: Throwable) {
                Log.e("getUnApprovedFarmerLand", "Error")
            }

            override fun onResponse(
                call: Call<FarmerDetailsWithLand>,
                response: Response<FarmerDetailsWithLand>
            ) {
                if (response.isSuccessful)
                    liveUnapprovedFarmersWithLands.value = response.body()
                Log.i("getUnApprovedFarmerLand", "GetUnApprovedFarmerSuccess")
            }
        })
    }


    fun getUnVerifiedFarmer() {
        Log.e("getUnVerifiedFarmer", "start")
        val retrofitCall = create(context).getUnVerifiedFarmer()
        retrofitCall.enqueue(object : Callback<List<FarmerRegDetails>> {
            override fun onFailure(call: Call<List<FarmerRegDetails>>, t: Throwable) {
                Log.e("getUnVerifiedFarmer", "Error")
            }

            override fun onResponse(
                call: Call<List<FarmerRegDetails>>,
                response: Response<List<FarmerRegDetails>>
            ) {
                if (response.isSuccessful)
                    liveUnVerifiedFarmerList.value = response.body()
                Log.i("getUnVerifiedFarmer", "getUnVerifiedFarmerSuccess")
            }
        })
    }

    fun getUnVerifiedFarmerByVill(villgeCode: String) {
        Log.e("UnVerifiedFarmerByVill", "start")
        val retrofitCall = create(context).getUnVerifiedFarmerByVill(villgeCode)
        retrofitCall.enqueue(object : Callback<List<FarmerRegDetails>> {
            override fun onFailure(call: Call<List<FarmerRegDetails>>, t: Throwable) {
                Log.e("UnVerifiedFarmerByVill", "Error")
            }

            override fun onResponse(
                call: Call<List<FarmerRegDetails>>,
                response: Response<List<FarmerRegDetails>>
            ) {
                if (response.isSuccessful)
                    liveUnVerifiedFarmerList.value = response.body()
                Log.i("UnVerifiedFarmerByVill", "UnVerifiedFarmerByVillSuccess")
            }
        })
    }

    fun getUnVerifiedFarmerById(regNo: String) {
        Log.e("getUnVerifiedFarmerById", "start")
        val retrofitCall = create(context).getUnVerifiedFarmerById(regNo)
        retrofitCall.enqueue(object : Callback<FarmerRegDetails> {
            override fun onFailure(call: Call<FarmerRegDetails>, t: Throwable) {
                Log.e("getUnVerifiedFarmerById", "Error")
            }

            override fun onResponse(
                call: Call<FarmerRegDetails>,
                response: Response<FarmerRegDetails>
            ) {
                if (response.isSuccessful)
                    liveUnVerifiedFarmer.value = response.body()
                Log.i("getUnVerifiedFarmerById", "getUnVerifiedFarmerByIdSuccess")
            }
        })
    }

    fun searchValidFarmerById(regNo: String) {
        Log.e("searchValidFarmerById", "start")
        val retrofitCall = create(context).searchValidFarmerById(regNo)
        retrofitCall.enqueue(object : Callback<FamilyRegDetails> {
            override fun onFailure(call: Call<FamilyRegDetails>, t: Throwable) {
                Log.e("searchValidFarmerById", "Error")
            }

            override fun onResponse(
                call: Call<FamilyRegDetails>,
                response: Response<FamilyRegDetails>
            ) {
                if (response.isSuccessful)
                    liveFarmerFamily.value = response.body()
                Log.i("searchValidFarmerById", "searchValidFarmerByIdSuccess")
            }
        })
    }


    fun saveApprovedFarmerDetails(farmerDetails: InputSubsidyFarmerDetailsRequest) {
        if (farmerDetails.ACStatus == 1) {
            val newPicFile: String = "${farmerDetails.RegistrationNo}.jpeg"
            val path =
                File(
                    Environment.getExternalStorageDirectory().toString(),
                    "Pictures/DBTAgri/$newPicFile"
                )
            var genClass: General = General()
            var bitImage = genClass.getBitmap(path.absolutePath)
            if (bitImage != null) {
                var imageBase64 = genClass.encodeTobase64(bitImage)
                if (imageBase64 != null) {
                    farmerDetails.ImageFile = imageBase64
                }
            }
        }
        Log.e("saveApprovedFarmer", "start")
        val retrofitCall = create(context).saveApprovedFarmerDetails(farmerDetails)
        retrofitCall.enqueue(object : Callback<InputSubsidyFarmerDetailsResponse> {
            override fun onFailure(call: Call<InputSubsidyFarmerDetailsResponse>, t: Throwable) {
                Log.e("saveApprovedFarmer", "Error")
            }

            override fun onResponse(
                call: Call<InputSubsidyFarmerDetailsResponse>,
                response: Response<InputSubsidyFarmerDetailsResponse>
            ) {
                if (response.isSuccessful) {
                    liveFarmerInputApproveUploadResponse.value = response.body()
                    Log.i("saveApprovedFarmer", "saveApprovedFarmerSuccess")
                }
            }
        })
    }


    fun acLogout() {
        Log.e("acLogout", "start")
        val retrofitCall = create(context).acLogout()
        retrofitCall.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("acLogout", "Error")
            }

            override fun onResponse(
                call: Call<Int>,
                response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    liveAcLogoutResponse.value = response.body()
                    Log.i("acLogout", "Success")
                } else {
                    liveAcLogoutResponse.value = 0
                }
            }
        })
    }

    fun coLogout() {
        Log.e("acLogout", "start")
        val retrofitCall = create(context).coLogout()
        retrofitCall.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("coLogout", "Error")
            }

            override fun onResponse(
                call: Call<Int>,
                response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    liveAcLogoutResponse.value = response.body()
                    Log.i("acLogout", "Success")
                } else {
                    liveAcLogoutResponse.value = 0
                }
            }
        })
    }


    //------------ PmKisan Verificatoin

    fun getUnVerifiedPMKisanList() {
        Log.e("getUnVerifiedPMKisan", "start")
        val retrofitCall = create(context).getUnVerifiedPMKisanFarmer()
        retrofitCall.enqueue(object : Callback<List<FarmerRegDetails>> {
            override fun onFailure(call: Call<List<FarmerRegDetails>>, t: Throwable) {
                Log.e("getUnVerifiedPMKisan", "Error")
            }

            override fun onResponse(
                call: Call<List<FarmerRegDetails>>,
                response: Response<List<FarmerRegDetails>>
            ) {
                if (response.isSuccessful)
                    liveUnVerifiedFarmerList.value = response.body()
                Log.i("getUnVerifiedPMKisan", "getUnVerifiedFarmerSuccess")
            }
        })
    }

    fun getUnVerifiedPMKisanFarmerByMobileNo(MobileNo: String) {
        Log.e("getUnVerifiedFarmerById", "start")
        val retrofitCall = create(context).getUnVerifiedPMKisanFarmerByMobileNo(MobileNo)
        retrofitCall.enqueue(object : Callback<List<FarmerRegDetails>> {
            override fun onFailure(call: Call<List<FarmerRegDetails>>, t: Throwable) {
                Log.e("getUnVerifiedFarmerById", "Error")
            }

            override fun onResponse(
                call: Call<List<FarmerRegDetails>>,
                response: Response<List<FarmerRegDetails>>
            ) {
                if (response.isSuccessful)
                    liveUnVerifiedPMkisanFarmer.value = response.body()
                Log.i("getUnVerifiedFarmerById", "getUnVerifiedFarmerByIdSuccess")
            }
        })
    }

    fun saveVerifiedPMKisanFarmerDetails(farmerDetails: PMKisanEligibleVerification) {
        Log.e("saveVerifiedPMKisan", "start")
        val retrofitCall = create(context).saveVerifiedPMKisanFarmerDetails(farmerDetails)
        retrofitCall.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("saveVerifiedPMKisan", "Error")
            }

            override fun onResponse(
                call: Call<Int>,
                response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    liveFarmerVerifiedPMKisanResponse.value = response.body()
                    Log.i("saveVerifiedPMKisan", "saveApprovedFarmerSuccess")
                } else {
                    liveFarmerVerifiedPMKisanResponse.value = 0
                }
            }
        })
    }

    //--PMMkisan Recovery


    fun getUnRecoveredPMKisanList() {
        Log.e("getUnRecoveredPMKisan", "start")
        val retrofitCall = create(context).getUnRecoveredFarmer()
        retrofitCall.enqueue(object : Callback<List<FarmerRegDetails>> {
            override fun onFailure(call: Call<List<FarmerRegDetails>>, t: Throwable) {
                Log.e("getUnRecoveredPMKisan", "Error")
            }

            override fun onResponse(
                call: Call<List<FarmerRegDetails>>,
                response: Response<List<FarmerRegDetails>>
            ) {
                if (response.isSuccessful) {
                    liveUnRecoveredFarmerList.value = response.body()
                    Log.i("getUnRecoveredPMKisan", "getUnRecoveredPMKisanSuccess")
                }
            }
        })
    }

    fun getUnRecoveredPMKisanFarmerByMobileNo(MobileNo: String) {
        Log.e("getUnVerifiedFarmerById", "start")
        val retrofitCall = create(context).getUnRecoveredPMKisanFarmerByMobileNo(MobileNo)
        retrofitCall.enqueue(object : Callback<List<FarmerRegDetails>> {
            override fun onFailure(call: Call<List<FarmerRegDetails>>, t: Throwable) {
                Log.e("getUnVerifiedFarmerById", "Error")
            }

            override fun onResponse(
                call: Call<List<FarmerRegDetails>>,
                response: Response<List<FarmerRegDetails>>
            ) {
                if (response.isSuccessful)
                    liveUnVerifiedPMkisanFarmer.value = response.body()
                Log.i("getUnVerifiedFarmerById", "getUnVerifiedFarmerByIdSuccess")
            }
        })
    }

    fun saveRecoveredFarmerDetails(farmerDetails: PMKisanRecovery) {
        Log.e("saveRecoveredPMKisan", "start")
        val retrofitCall = create(context).saveRecoveredFarmerDetails(farmerDetails)
        retrofitCall.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("saveRecoveredPMKisan", "Error")
            }

            override fun onResponse(
                call: Call<Int>,
                response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    liveFarmerRecoverdPMKisanResponse.value = response.body()
                    Log.i("saveRecoveredPMKisan", "Success")
                } else {
                    liveFarmerRecoverdPMKisanResponse.value = 0
                }
            }
        })
    }


    //----- Farmer Call List


    fun getFarmerCallList(
        distCode: String,
        blockCode: String,
        panchayatCode: String,
        villageCode: String
    ) {
        Log.e("getUnVerifiedFarmer", "start")
        val retrofitCall =
            create(context).getFarmerCallList(distCode, blockCode, panchayatCode, villageCode)
        retrofitCall.enqueue(object : Callback<List<FarmerRegDetails>> {
            override fun onFailure(call: Call<List<FarmerRegDetails>>, t: Throwable) {
                Log.e("getUnVerifiedFarmer", "Error")
            }

            override fun onResponse(
                call: Call<List<FarmerRegDetails>>,
                response: Response<List<FarmerRegDetails>>
            ) {
                if (response.isSuccessful)
                    liveFarmerCallList.value = response.body()
                Log.i("getUnVerifiedFarmer", "getUnVerifiedFarmerSuccess")
            }
        })
    }


    fun getDistrictList() {
        Log.e("DistrictList", "start")
        val retrofitCall =
            create(context).getDistrictList()
        retrofitCall.enqueue(object : Callback<List<DropDownDataset>> {
            override fun onFailure(call: Call<List<DropDownDataset>>, t: Throwable) {
                Log.e("DistrictList", "Error")
            }

            override fun onResponse(
                call: Call<List<DropDownDataset>>,
                response: Response<List<DropDownDataset>>
            ) {
                if (response.isSuccessful)
                    liveDistrictList.value = response.body()
                Log.i("getDistrictList", "DistrictList")
            }
        })
    }

    fun getBlockList(distCode: String) {
        Log.e("getBlockList", "start")
        val retrofitCall =
            create(context).getBlockList(distCode)
        retrofitCall.enqueue(object : Callback<List<DropDownDataset>> {
            override fun onFailure(call: Call<List<DropDownDataset>>, t: Throwable) {
                Log.e("getBlockList", "Error")
            }

            override fun onResponse(
                call: Call<List<DropDownDataset>>,
                response: Response<List<DropDownDataset>>
            ) {
                if (response.isSuccessful)
                    liveBlockList.value = response.body()
                Log.i("getBlockList", "getBlockList")
            }
        })
    }

    fun getPanchaytList(blockCode: String) {
        Log.e("getPanchaytList", "start")
        val retrofitCall =
            create(context).getPanchayatList(blockCode)
        retrofitCall.enqueue(object : Callback<List<DropDownDataset>> {
            override fun onFailure(call: Call<List<DropDownDataset>>, t: Throwable) {
                Log.e("getPanchaytList", "Error")
            }

            override fun onResponse(
                call: Call<List<DropDownDataset>>,
                response: Response<List<DropDownDataset>>
            ) {
                if (response.isSuccessful)
                    livePanchayatList.value = response.body()
                Log.i("getPanchaytList", "getBlockList")
            }
        })
    }

    fun getVillageList(panchayatCode: String) {
        Log.e("getVillageList", "start")
        val retrofitCall =
            create(context).getVillageList(panchayatCode)
        retrofitCall.enqueue(object : Callback<List<DropDownDataset>> {
            override fun onFailure(call: Call<List<DropDownDataset>>, t: Throwable) {
                Log.e("getVillageList", "Error")
            }

            override fun onResponse(
                call: Call<List<DropDownDataset>>,
                response: Response<List<DropDownDataset>>
            ) {
                if (response.isSuccessful)
                    liveVillageList.value = response.body()
                Log.i("getVillageList", "getBlockList")
            }
        })
    }

    fun SaveFarmerCallFeedback(farmerDetails: FarmerCallVerification) {
        Log.e("saveFarmerFeedb", "start")
        val retrofitCall = create(context).SaveFarmerCallFeedback(farmerDetails)
        retrofitCall.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("saveFarmerFeedb", "Error")
            }

            override fun onResponse(
                call: Call<Int>,
                response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    liveFarmerCallVerifiedResponse.value = response.body()
                    Log.i("saveFarmerFeedb", "saveFarmerFeedbSuccess")
                } else {
                    liveFarmerCallVerifiedResponse.value = 0
                }
            }
        })
    }


    //------------ Social Audit------Start-------//

    fun getUnVerifiedSocialAuditPMKisanFarmer() {
        Log.e("getSocialUnVerifiedPMK", "start")
        val retrofitCall = create(context).getUnVerifiedSocialAuditPMKisanFarmer()
        retrofitCall.enqueue(object : Callback<List<FarmerRegDetails>> {
            override fun onFailure(call: Call<List<FarmerRegDetails>>, t: Throwable) {
                Log.e("getSocialUnVerifiedPMK", "Error")
            }

            override fun onResponse(
                call: Call<List<FarmerRegDetails>>,
                response: Response<List<FarmerRegDetails>>
            ) {
                if (response.isSuccessful)
                    liveUnVerifiedSocialAuditFarmerList.value = response.body()
                Log.i("getSocialUnVerifiedPMK", "getUnVerifiedFarmerSuccess")
            }
        })
    }


    fun saveVerifiedSocialAuditPMKisanFarmerDetails(farmerDetails: PMKisanSocialAuditModel) {
        Log.e("saveVerifiedSocialPMK", "start")
        val retrofitCall =
            create(context).saveVerifiedSocialAuditPMKisanFarmerDetails(farmerDetails)
        retrofitCall.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("saveVerifiedSocialPMK", "Error")
            }

            override fun onResponse(
                call: Call<Int>,
                response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    liveFarmerVerifiedSocialAuditPMKisanResponse.value = response.body()
                    Log.i("saveVerifiedSocialPMK", "saveApprovedFarmerSuccess")
                } else {
                    liveFarmerVerifiedSocialAuditPMKisanResponse.value = 0
                }
            }
        })
    }

    //------------ Social Audit------End-------//

    //------------ PM Kisan AC Verify------Start------//
    fun getUnVerifiedACPMKisanFarmer() {
        Log.e("getSocialUnVerifiedPMK", "start")
        val retrofitCall = create(context).getUnVerifiedACPMKisanFarmer()
        retrofitCall.enqueue(object : Callback<List<FarmerRegDetails>> {
            override fun onFailure(call: Call<List<FarmerRegDetails>>, t: Throwable) {
                Log.e("getSocialUnVerifiedPMK", "Error")
            }

            override fun onResponse(
                call: Call<List<FarmerRegDetails>>,
                response: Response<List<FarmerRegDetails>>
            ) {
                if (response.isSuccessful)
                    liveUnVerifiedSocialAuditFarmerList.value = response.body()
                Log.i("getSocialUnVerifiedPMK", "getUnVerifiedFarmerSuccess")
            }
        })
    }

    fun saveVerifiedACPMKisanFarmerDetails(farmerDetails: PMKisanVerifyACModel) {
        Log.e("saveVerifiedSocialPMK", "start")
        val retrofitCall =
            create(context).saveVerifiedACPMKisanFarmerDetails(farmerDetails)
        retrofitCall.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("saveVerifiedSocialPMK", "Error")
            }

            override fun onResponse(
                call: Call<Int>,
                response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    liveFarmerVerifiedPMKisanResponse.value = response.body()
                    Log.i("saveVerifiedSocialPMK", "saveApprovedFarmerSuccess")
                } else {
                    liveFarmerVerifiedPMKisanResponse.value = 0
                }
            }
        })
    }
    //------------ PM Kisan AC Verify------End------//

    //------------ PM Kisan CO Verify------Start------//

    fun getUnVerifiedCOPMKisanFarmer() {
        Log.e("getCOUnVerifiedPMK", "start")
        val retrofitCall = create(context).getUnVerifiedCOPMKisanFarmer()
        retrofitCall.enqueue(object : Callback<List<FarmerRegDetails>> {
            override fun onFailure(call: Call<List<FarmerRegDetails>>, t: Throwable) {
                Log.e("getCOUnVerifiedPMK", "Error")
            }

            override fun onResponse(
                call: Call<List<FarmerRegDetails>>,
                response: Response<List<FarmerRegDetails>>
            ) {
                if (response.isSuccessful)
                    liveUnVerifiedCOAuditFarmerList.value = response.body()
                Log.i("getCOUnVerifiedPMK", "getUnVerifiedFarmerSuccess")
            }
        })
    }


    fun saveVerifiedCOPMKisanFarmerDetails(farmerDetails: PMKisanVerifyCOModel) {
        Log.e("saveVerifiedSocialPMK", "start")
        val retrofitCall =
            create(context).saveVerifiedCOPMKisanFarmerDetails(farmerDetails)
        retrofitCall.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("saveVerifiedSocialPMK", "Error")
            }

            override fun onResponse(
                call: Call<Int>,
                response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    liveFarmerVerifiedPMKisanResponse.value = response.body()
                    Log.i("saveVerifiedSocialPMK", "saveApprovedFarmerSuccess")
                } else {
                    liveFarmerVerifiedPMKisanResponse.value = 0
                }
            }
        })
    }

    //------------ PM Kisan CO Verify------End------//


    //------------ PM Kisan ADM Verify------Start------//

    fun getUnVerifiedADMPMKisanFarmer() {
        Log.e("getADMUnVerifiedPMK", "start")
        val retrofitCall = create(context).getUnVerifiedADMPMKisanFarmer()
        retrofitCall.enqueue(object : Callback<List<FarmerRegDetails>> {
            override fun onFailure(call: Call<List<FarmerRegDetails>>, t: Throwable) {
                Log.e("getADMUnVerifiedPMK", "Error")
            }

            override fun onResponse(
                call: Call<List<FarmerRegDetails>>,
                response: Response<List<FarmerRegDetails>>
            ) {
                if (response.isSuccessful)
                    liveUnVerifiedADMAuditFarmerList.value = response.body()
                Log.i("getADMUnVerifiedPMK", "getUnVerifiedFarmerSuccess")
            }
        })
    }

    fun saveVerifiedADMPMKisanFarmerDetails(farmerDetails: PMKisanVerifyADMModel) {
        Log.e("saveVerifiedSocialPMK", "start")
        val retrofitCall =
            create(context).saveVerifiedADMPMKisanFarmerDetails(farmerDetails)
        retrofitCall.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("saveVerifiedSocialPMK", "Error")
            }

            override fun onResponse(
                call: Call<Int>,
                response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    liveFarmerVerifiedPMKisanResponse.value = response.body()
                    Log.i("saveVerifiedSocialPMK", "saveApprovedFarmerSuccess")
                } else {
                    liveFarmerVerifiedPMKisanResponse.value = 0
                }
            }
        })
    }

    //------------ PM Kisan ADM Verify------End------//


    //------------ PMKISAN Ankshan-----Start-------//

    fun GetCountPMKISANAnkshen() {
        Log.e("getSocialUnVerifiedPMK", "start")
        val retrofitCall = create(context).GetCountPMKISANAnkshen()
        retrofitCall.enqueue(object : Callback<PMKISANAnkshenModel> {
            override fun onFailure(call: Call<PMKISANAnkshenModel>, t: Throwable) {
                Log.e("getSocialUnVerifiedPMK", "Error")
            }

            override fun onResponse(
                call: Call<PMKISANAnkshenModel>,
                response: Response<PMKISANAnkshenModel>
            ) {
                if (response.isSuccessful)
                    livePMKISANAnkshanDistCount.value = response.body()
                Log.i("getSocialUnVerifiedPMK", "getUnVerifiedFarmerSuccess")
            }
        })
    }


    fun savePMKISANAnkshenDetails(farmerDetails: PMKISANAnkshenModel) {
        Log.e("saveVerifiedSocialPMK", "start")
        val retrofitCall =
            create(context).SavePMKISANAnkshen(farmerDetails)
        retrofitCall.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("saveVerifiedSocialPMK", "Error")
            }

            override fun onResponse(
                call: Call<Int>,
                response: Response<Int>
            ) {
                if (response.isSuccessful) {
                    livePMKISANAnkshanResponse.value = response.body()
                    Log.i("saveVerifiedSocialPMK", "saveApprovedFarmerSuccess")
                } else {
                    livePMKISANAnkshanResponse.value = 0
                }
            }
        })
    }

    //------------ PMKISAN Ankshan------End-------//
}