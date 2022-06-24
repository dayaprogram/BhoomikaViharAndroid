package com.bseb.crossword.RemoteDataRepository


import com.bhoomikabihar.surveyapp.Model.*
import com.example.dbtagri.RemoteDataRepository.Constants
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("agriculturewebservice.asmx/SendSMS")
    fun getFarmerSMS(
        @Field("RegId") registrationId: String,
        @Field("Uid") userID: String, @Field("password") password: String
    ): Call<String>


    @FormUrlEncoded
    @POST("RegFarmer/BiharAgariWebService.asmx/SendSMSNew")
    fun sendOtpSMS(
        @Field("mobileNo") mobileNo: String,
        @Field("Uid") userID: String,
        @Field("password") password: String,
        @Field("OTPNew") OTPNew: String
    ): Call<String>

    @POST(Constants.LOGIN_URL)
    @FormUrlEncoded
    @Headers(
        "Accept: application/json",
        "Content-Type: application/x-www-form-urlencoded",
        "accept-encoding: gzip, deflate",
    )
    fun login(
        @Field("grant_type") grantType: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("api/AgriCoordinator/Logout")
    fun acLogout(): Call<Int>

    @GET("api/AgriCoordinator/Logout")
    fun coLogout(): Call<Int>

    @GET("api/AgriCoordinator/Logout")
    fun admLogout(): Call<Int>

    @GET("api/AgriCoordinator/Logout")
    fun daoLogout(): Call<Int>


    @GET("api/AgriCoordinator/GetUnApprovedFarmer")
    fun getUnApprovedFarmer(): Call<List<FarmerDetails>>

    @GET("api/AgriCoordinator/GetUnApprovedFarmerLand")
    fun getUnApprovedFarmerLand(@Query("registrationId") regNo: String): Call<List<LandDetails>>

    // get all farmer list with land details
    @GET("api/AgriCoordinator/getUnApprovedFarmersAndLandList")
    fun getUnApprovedFarmersAndLandList(): Call<FarmerDetailsWithLand>

    @GET("api/AgriCoordinator/GetUnVerifiedFarmer")
    fun getUnVerifiedFarmer(): Call<List<FarmerRegDetails>>

    @GET("api/AgriCoordinator/GetUnVerifiedFarmerByVill")
    fun getUnVerifiedFarmerByVill(@Query("villgeCode") villgeCode: String): Call<List<FarmerRegDetails>>

    @GET("api/AgriCoordinator/GetUnVerifiedFarmerById")
    fun getUnVerifiedFarmerById(@Query("registrationNo") regNo: String): Call<FarmerRegDetails>

    @GET("api/AgriCoordinator/SearchValidFarmerById")
    fun searchValidFarmerById(@Query("registrationNo") regNo: String): Call<FamilyRegDetails>

    @POST("api/AgriCoordinator/SaveApprovedInputSubDtlwrong")
    fun saveApprovedFarmerDetails(@Body farmerDetails: InputSubsidyFarmerDetailsRequest): Call<InputSubsidyFarmerDetailsResponse>


    @GET("api/PMKisan/GetUnVerifiedFarmer")
    fun getUnVerifiedPMKisanFarmer(): Call<List<FarmerRegDetails>>

    @POST("api/PMKisan/SaveVerifiedFarmerDetails")
    fun saveVerifiedPMKisanFarmerDetails(@Body farmerDetails: PMKisanEligibleVerification): Call<Int>

    @GET("api/PMKisan/GetUnVerifiedFarmerByMobileNo")
    fun getUnVerifiedPMKisanFarmerByMobileNo(@Query("MobileNo") MobileNo: String): Call<List<FarmerRegDetails>>

    @GET("api/PMKisan/GetUnRecoveredFarmer")
    fun getUnRecoveredFarmer(): Call<List<FarmerRegDetails>>

    @GET("api/PMKisan/GetUnRecoveredFarmerByMobileNo")
    fun getUnRecoveredPMKisanFarmerByMobileNo(@Query("MobileNo") MobileNo: String): Call<List<FarmerRegDetails>>

    @POST("api/PMKisan/SaveRecoveredFarmerDetails")
    fun saveRecoveredFarmerDetails(@Body farmerDetails: PMKisanRecovery): Call<Int>

    //---- Farmer Call List
    @GET("api/FarmerCall/GetFarmerList")
    fun getFarmerCallList(
        @Query("DistCode") distCode: String,
        @Query("BlockCode") blockCode: String,
        @Query("PanchayatCode") panchayatCode: String,
        @Query("VillageCode") villageCode: String
    ): Call<List<FarmerRegDetails>>

    @GET("api/FarmerCall/GetDistrictList")
    fun getDistrictList(): Call<List<DropDownDataset>>

    @GET("api/FarmerCall/GetBlockList")
    fun getBlockList(@Query("DistCode") districtCode: String): Call<List<DropDownDataset>>

    @GET("api/FarmerCall/GetPanchayatList")
    fun getPanchayatList(@Query("BlockCode") blockCode: String): Call<List<DropDownDataset>>

    @GET("api/FarmerCall/GetVillageList")
    fun getVillageList(@Query("PanchayatCode") panchayatCode: String): Call<List<DropDownDataset>>

    @POST("api/FarmerCall/SaveFarmerCallFeedback")
    fun SaveFarmerCallFeedback(@Body farmerDetails: FarmerCallVerification): Call<Int>


    //----Social Audit---------------- Start-------//

    @GET("api/PMKisan/GetUnVerifiedFarmer")
    fun getUnVerifiedSocialAuditPMKisanFarmer(): Call<List<FarmerRegDetails>>

    @POST("api/PMKisan/SaveVerifiedFarmerDetails")
    fun saveVerifiedSocialAuditPMKisanFarmerDetails(@Body farmerDetails: PMKisanSocialAuditModel): Call<Int>

    //----Social Audit----------------End-------//

    //----PM Kisan AC Verify---------------- Start-------//

    @GET("api/PmKisanVerifyAC/GetUnVerifiedFarmer")
    fun getUnVerifiedACPMKisanFarmer(): Call<List<FarmerRegDetails>>

    @POST("api/PmKisanVerifyAC/SaveVerifiedFarmerDetails")
    fun saveVerifiedACPMKisanFarmerDetails(@Body farmerDetails: PMKisanVerifyACModel): Call<Int>

    //----PM Kisan AC Verify----------------End-------//


    //----PM Kisan CO Verify---------------- Start-------//

    @GET("api/PmKisanVerifyCO/GetUnVerifiedFarmer")
    fun getUnVerifiedCOPMKisanFarmer(): Call<List<FarmerRegDetails>>

    @POST("api/PmKisanVerifyCO/SaveVerifiedFarmerDetails")
    fun saveVerifiedCOPMKisanFarmerDetails(@Body farmerDetails: PMKisanVerifyCOModel): Call<Int>

    //----PM Kisan CO Verify----------------End-------//

    // ----PM Kisan ADM Verify---------------- Start-------//

    @GET("api/PmKisanVerifyADM/GetUnVerifiedFarmer")
    fun getUnVerifiedADMPMKisanFarmer(): Call<List<FarmerRegDetails>>

    @POST("api/PmKisanVerifyADM/SaveVerifiedFarmerDetails")
    fun saveVerifiedADMPMKisanFarmerDetails(@Body farmerDetails: PMKisanVerifyADMModel): Call<Int>

    //----PM Kisan ADM Verify----------------End-------//

    //----PMKISAN Ankshan---------------- Start-------//

    @GET("api/PMKISANAnkshan/GetCountPMKISANAnkshen")
    fun GetCountPMKISANAnkshen(): Call<PMKISANAnkshenModel>

    @POST("api/PMKISANAnkshan/SavePMKISANAnkshen")
    fun SavePMKISANAnkshen(@Body data: PMKISANAnkshenModel): Call<Int>

    //----Social Audit----------------End-------//
}


