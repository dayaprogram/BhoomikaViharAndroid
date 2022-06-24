package com.example.dbtagri.LocalDataRepository.Dao

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @NonNull
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?
)


@Entity(tableName = "InputSubsidyApplicationLocal")
data class InputSubsidyApplicationLocal(
    @NonNull
//    @PrimaryKey
    @ColumnInfo(name = "RegistrationNo")
    var registrationNo: String,
    @NonNull
    @ColumnInfo(name = "ApplicationID")
    var applicationID: String,


    @ColumnInfo(name = "ApplicantName")
    var applicantName: String? = "",

    @ColumnInfo(name = "FatherHusbandName")
    var fatherHusbandName: String? = "",

    @ColumnInfo(name = "TotalAffectedRakwa")
    var totalAffectedRakwa: Double? = 0.0,

    @ColumnInfo(name = "TotalSubsidy")
    var totalSubsidy: Double? = 0.0,

    @ColumnInfo(name = "MobileNo")
    var mobileNo: String? = "",

    @ColumnInfo(name = "DistCode")
    var distCode: Int? = 0,

    @ColumnInfo(name = "BlockCode")
    var blockCode: Int? = 0,

    @ColumnInfo(name = "PanchayatCode")
    var panchayatCode: String? = "",

    @ColumnInfo(name = "SchemeType")
    var schemeType: Int? = 0,

    @ColumnInfo(name = "ACStatus")
    var aCStatus: Int? = 0,

    @ColumnInfo(name = "ACRemarks")
    var aCRemarks: String? = "",

    @ColumnInfo(name = "ACReason")
    var ACReason: String? = "",

    @ColumnInfo(name = "TotalApprovedLand")
    var totalApprovedLand: Double? = 0.0,

    @ColumnInfo(name = "TotalApprovedAmt")
    var totalApprovedAmt: Double? = 0.0,

    @ColumnInfo(name = "PhotoLatitude")
    var photoLatitude: Double? = 0.0,

    @ColumnInfo(name = "PhotoLongitude")
    var photoLongitude: Double? = 0.0,

    @ColumnInfo(name = "IMEINo")
    var imeiNo: String? = "",

    @ColumnInfo(name = "ActionTakenDtTime")
    var actionTakenDtTime: String? = ""
) {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "PhotoPath")
    var photoPath: String? = ""

    @ColumnInfo(name = "FarmerName1")
    var FarmerName1: String? = ""

    @ColumnInfo(name = "FarmerMobile1")
    var FarmerMobile1: String? = ""

    @ColumnInfo(name = "FarmerName2")
    var FarmerName2: String? = ""

    @ColumnInfo(name = "FarmerMobile2")
    var FarmerMobile2: String? = ""

    @ColumnInfo(name = "FinalSubmitFlag")
    var FinalSubmitFlag: Int? = 0

    @ColumnInfo(name = "SoilTest")
    var SoilTest: String? = ""
}


@Entity(
    tableName = "InputSubsidyLandLocal"
//    ,
//    foreignKeys = [ForeignKey(
//        entity = InputSubsidyApplicationLocal::class,
//        parentColumns = ["RegistrationNo"],
//        childColumns = ["RegistrationNo"],
//        onDelete = CASCADE
//    )]
)
//
//@Entity(tableName = "InputSubsidyLandLocal")
data class InputSubsidyLandLocal(
    @ColumnInfo(name = "RegistrationNo") var registrationNo: String,
    @ColumnInfo(name = "ApplicationID") var applicationID: String?,
    @ColumnInfo(name = "KisanType") var KisanType: Int?,
    @ColumnInfo(name = "CropType") var CropType: Int?,
    @ColumnInfo(name = "LandType") var LandType: Int?,
    @ColumnInfo(name = "KhataNo") var KhataNo: String?,
    @ColumnInfo(name = "ThanaNo") var ThanaNo: String?,
    @ColumnInfo(name = "KeshraNo") var KeshraNo: String?,
    @ColumnInfo(name = "Affectedrakwa") var Affectedrakwa: Double?,
    @ColumnInfo(name = "AnudanAmount") var AnudanAmount: Double?
) {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var landId: Int = 0
}
