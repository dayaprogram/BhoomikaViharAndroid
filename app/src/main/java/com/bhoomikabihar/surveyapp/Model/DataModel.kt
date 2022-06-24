package com.bhoomikabihar.surveyapp.Model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    var authToken: String,
    @SerializedName("token_type")
    var tokenType: String,
    @SerializedName("expires_in")
    var expiresIn: Int,
    @SerializedName("userId")
    var userId: String,
    @SerializedName("Name")
    var name: String,
    @SerializedName("userrole")
    var userRole: String,
    @SerializedName("Mobileno")
    var MobileNo: String,
    @SerializedName(".issued")
    var issued: String,
    @SerializedName(".expires")
    var expires: String,
    @SerializedName("distId")
    var distId: String,
    @SerializedName("blockId")
    var blockId: String,
    @SerializedName("panchayatId")
    var panchayatId: String,

    )

//data class LoginRequest(
//    @SerializedName("grant_type")
//    var grant_type: String = "password",
//    @SerializedName("username")
//    var username: String,
//    @SerializedName("password")
//    var password: String
//)

data class User(
    @SerializedName("id")
    var userid: String,

    @SerializedName("first_name")
    var name: String,

    @SerializedName("last_name")
    var mobileNo: String,
    var userRole: String,
    var distId: String,
    var blockId: String,
    var panchayat: String
)

data class Post(
    @SerializedName("id")
    var id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("content")
    var content: String
)


data class PostsResponse(
    @SerializedName("status_code")
    var status: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("posts")
    var posts: List<Post>
)

public open class DashBoardContain(
    val intentID: String,
    var title: String,
    val shortdesc: String,
    val image: Int

)

public class FarmerDetails(
    @SerializedName("registration_ID")
    val regId: String,
    @SerializedName("application_ID")
    val appId: String,
    @SerializedName("applicantName")
    val applicantName: String,
    @SerializedName("father_Husband_Name")
    val father_Husband_Name: String,
    @SerializedName("totalAffectedRakwa")
    val totalAffectedRakwa: Double,
    @SerializedName("totalSubsidy")
    val totalSubsidy: Double,
    @SerializedName("mobileNo")
    val mobileNo: String,
    @SerializedName("schemeType")
    val schemeType: Int,
    @SerializedName("distcode")
    val distcode: Int,
    @SerializedName("blockCode")
    val blockCode: Int,
    @SerializedName("panchayatCode")
    val panchayatCode: String,
)

class LandDetails(
    @SerializedName("registrationID")
    var frmRegID: String = "",
    @SerializedName("application_ID")
    var frmAppId: String = "",
    @SerializedName("kisanType")
    var kisanType: Int = 0,
    @SerializedName("cropType")
    var cropType: Int = 0,
    @SerializedName("landType")
    var landType: Int = 0,
    @SerializedName("khatano")
    var khatano: String = "",
    @SerializedName("thanano")
    var thanaNo: String = "",
    @SerializedName("keshrano")
    var keshrano: String = "",
    @SerializedName("affectedrakwa")
    var affectedRakwa: Double = 0.0,
    @SerializedName("anudanAmount")
    var anudanAmount: Double = 0.0,
    var approvedRakwa: Double = 0.0,
    var approvedAmt: Double = 0.0,
    var approvedLandType: Int = 0,
    var landId: Int = 0,
)

public class FarmerDetailsWithLand(
    @SerializedName("farmerDetails")
    var FarmerDetails: List<FarmerDetails>,
    @SerializedName("landDetails")
    var LandDetails: List<LandDetails>,
)

public open class Dropdown(
    var id: Int,
    var name: String
)


public class FarmerRegDetails
    (
    @SerializedName("registration_ID")
    var Registration_ID: String,
    @SerializedName("aadhaarNumber")
    var AadhaarNumber: String,
    @SerializedName("farmer_FName")
    var Farmer_FName: String,
    @SerializedName("farmer_LName")
    var Farmer_LName: String?,
    @SerializedName("father_Husband_Name")
    var Father_Husband_Name: String,
    @SerializedName("gender")
    var Gender: String,
    @SerializedName("dob")
    var DOB: String,
    @SerializedName("castCateogary")
    var CastCateogary: String,
    @SerializedName("villageCode")
    var VillageCode: String,
    @SerializedName("villName")
    var VillName: String?,
    @SerializedName("mobileNumber")
    var MobileNumber: String,
    @SerializedName("farmerGrade")
    var FarmerGrade: String?,
    @SerializedName("noOfInstallments")
    var NoOfInstallments: Int?,

    )

public class FamilyRegDetails
    (
    @SerializedName("registration_ID")
    var Registration_ID: String,
    @SerializedName("farmer_FName")
    var Farmer_FName: String,
    @SerializedName("farmer_LName")
    var Farmer_LName: String,
    @SerializedName("father_Husband_Name")
    var Father_Husband_Name: String,
    @SerializedName("villageCode")
    var VillageCode: String,
    @SerializedName("villName")
    var VillName: String,
)

public class DropDownDataset
    (
    @SerializedName("values")
    var values: String,
    @SerializedName("options")
    var options: String
)

public class FarmerVerificationRequest {
    var RegistrationNo: String = ""
    var FarmerType: Int = 0
    var IsDeath: Boolean = true
    var ResideInVill: Boolean = false
    var SoilTestStatus: Boolean = false
    var FamilyExist: Boolean = false
    var ActionLat: Double = 0.0
    var ActionLong: Double = 0.0
    var IMEINo: String = ""
    var DistrictCode: String = ""
    var BlockCode: String = ""
    var PanchayatCode: String = ""
    var ActionBy: String = ""
    var OtherRemarks: String = ""
    lateinit var FamilyList: List<String>
}

public class InputSubsidyFarmerDetailsRequest(
    var RegistrationNo: String = "",
    var ApplicationID: String = "",
    var DocumentFileName: String = "",
    var ACStatus: Int = 0,
    var ACRemarks: String = "",
    var ACRejectReason: String = "",
    var TotalApprovedLand: Double = 0.0,
    var TotalApprovedAmt: Double = 0.0,
    var PhotoLatitude: Double = 0.0,
    var PhotoLongitude: Double = 0.0,
    var PhotoPath: String = "",
    var PhotoTakenDtTime: String = "",
    var FarmerName1: String = "",
    var FarmerMobile1: String = "",
    var FarmerName2: String = "",
    var FarmerMobile2: String = "",
    var SoilTest: String = "",
    var imeiNo: String? = ""
) {
    var Distcode: Int = 0
    var BlockCode: Int = 0
    var PanchayatCode: String = ""

    //    var DocumentFileName: String = ""
//    var Distcode: Int = 0
//    var BlockCode: Int = 0
//    var PanchayatCode: String = ""
//    var ACStatus: Int = 0
//    var ACRemarks: String = ""
//    var ACRejectReason: String = ""
//    var TotalApprovedLand: Double = 0.0
//    var TotalApprovedAmt: Double = 0.0
//    var PhotoLatitude: Double = 0.0
//    var PhotoLongitude: Double = 0.0
//    var PhotoPath: String = ""
//    var PhotoTakenDtTime: String = ""
//    var FarmerName1: String = ""
//    var FarmerMobile1: String = ""
//    var FarmerName2: String = ""
//    var FarmerMobile2: String = ""
//    var SoilTest: String = ""
//    var imeiNo: String? = ""
    var ImageFile: String = ""
}

public class InputSubsidyFarmerDetailsResponse(
    @SerializedName("registrationNo")
    var RegistrationNo: String = "",
    @SerializedName("applicationID")
    var ApplicationID: String = "",
    @SerializedName("uploadFlag")
    var uploadFlag: Int
)


public class FamilyRegistration
    (
    var FamilyRegNo: String
)


public class PMKisanRecovery {
    var RegistrationNo: String = ""
    var MobileNo: String = ""

    var RefundStatus: String = ""
    var RefundMethod: String = ""
    var RefundTrnRefNO: String = ""
    var RefundTrnDate: String = ""
    var RefundAmt: Double = 0.0

    var ActionLat: Double = 0.0
    var ActionLong: Double = 0.0
    var IMEINo: String = ""
    var DistrictCode: String = ""
    var BlockCode: String = ""
    var PanchayatCode: String = ""
    var ActionBy: String = ""
    var OtherRemarks: String = ""
}


public class PMKisanEligibleVerification {
    var RegistrationNo: String = ""
    var FarmerEntitlement: String = ""
    var IncomeTaxPayer: String = ""
    var Minister: String = ""
    var GovtEmp: String = ""
    var RetiredEmp: String = ""
    var ConstitutionalPostEmp: String = ""
    var ProfessionalEmp: String = ""
    var PhyVerifResponse: String = ""
    var PhyVerifDate: String = ""
    var PhyVerifReason: String = ""

    var LandHave: String = ""
    var FamilyGetBen: String = ""

    var MobileNo: String = ""
    var AadharNo: String = ""
    var ActionLat: Double = 0.0
    var ActionLong: Double = 0.0
    var IMEINo: String = ""
    var DistrictCode: String = ""
    var BlockCode: String = ""
    var PanchayatCode: String = ""
    var ActionBy: String = ""
    var OtherRemarks: String = ""
}

public class FarmerCallVerification {
    var RegistrationNo: String = ""
    var MobileNo: String = ""
    var feedback: String = ""
    var feedbackRemarks: String = ""
    var ActionLat: Double = 0.0
    var ActionLong: Double = 0.0
    var IMEINo: String = ""
    var ActionBy: String = ""
}

public open class SpinnerObj(
    var id: Int,
    var name: String,
    var isActive: Boolean
)

public class PMKisanSocialAuditModel {
    var RegistrationNo: String = ""
    var FarmerEntitlement: String = ""
    var IncomeTaxPayer: String = ""
    var Minister: String = ""
    var GovtEmp: String = ""
    var RetiredEmp: String = ""
    var ConstitutionalPostEmp: String = ""
    var ProfessionalEmp: String = ""
    var PhyVerifResponse: String = ""
    var PhyVerifDate: String = ""
    var PhyVerifReason: String = ""

    var LandHave: String = ""
    var FamilyGetBen: String = ""

    var MobileNo: String = ""
    var AadharNo: String = ""
    var ActionLat: Double = 0.0
    var ActionLong: Double = 0.0
    var IMEINo: String = ""
    var DistrictCode: String = ""
    var BlockCode: String = ""
    var PanchayatCode: String = ""
    var ActionBy: String = ""
    var OtherRemarks: String = ""
}

public class PMKisanVerifyACModel {
    var RegistrationNo: String = ""
    var ACDocVerify: String = ""
    var ACFamilyBenify: String = ""
    var ACPersonalInfoVerify: String = ""
    var ACFamilyInfoVerify: String = ""
    var ACBankInfoVerify: String = ""
    var ACLandInfoVerify: String = ""

    var ACRayatInfoVerify: String = ""
    var ACTopolandInfoVerify: String = ""
    var ACAllInfoVerify: String = ""
    var ACLatitude: Double = 0.0
    var ACLongitude: Double = 0.0
    var ACIMEINo: String = ""
    var MobileNo: String = ""
    var ActionBy: String = ""
    var Remarks: String = ""
}

public class PMKisanVerifyCOModel {
    var RegistrationNo: String = ""
    var CODocVerify: String = ""
    var CODocRecent: String = ""
    var COApprove: String = ""


    var COLatitude: Double = 0.0
    var COLongitude: Double = 0.0
    var COIMEINo: String = ""
    var ActionBy: String = ""
    var Remarks: String = ""
}

public class PMKisanVerifyADMModel {
    var RegistrationNo: String = ""
    var ADMDocVerify: String = ""
    var ADMDocRecent: String = ""
    var ADMDocInfo: String = ""
    var ADMApprove: String = ""


    var ADMLatitude: Double = 0.0
    var ADMLongitude: Double = 0.0
    var ADMIMEINo: String = ""
    var ActionBy: String = ""
    var Remarks: String = ""
}

public class PMKISANAnkshenModel {
    @SerializedName("totalVillCount")
    var TotalVillCount: Int = 0

    @SerializedName("totalBenifCount")
    var TotalBenifCount: Int = 0

    @SerializedName("revVillCovered")
    var RevVillCovered: Int = 0

    @SerializedName("benifAuditCompleted")
    var BenifAuditCompleted: Int = 0

    @SerializedName("ineliglibleBenif")
    var IneliglibleBenif: Int = 0

    @SerializedName("eligibleNonBenif")
    var EligibleNonBenif: Int = 0
}