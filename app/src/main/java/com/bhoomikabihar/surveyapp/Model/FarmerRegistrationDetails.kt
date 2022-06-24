package com.example.dbtagri.Model

import com.google.gson.annotations.SerializedName
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "DBTFarmerRegistration", strict = false)
class DBTFarmerRegistration {
    @Element(name = "Exist")
    var Exist: String = ""

    @Element(name = "Registration_ID")
    var Registration_ID: String = ""

    @Element(name = "FarmerName")
    var FarmerName: String = ""

    @Element(name = "Father_Husband_Name")
    var Father_Husband_Name: String = ""

    @Element(name = "CastCateogary")
    var CastCateogary: String = ""

    @Element(name = "AadhaarNo")
    var AadhaarNo: String = ""

    @Element(name = "DistrictCode")
    var DistrictCode: String = ""

    @Element(name = "DistrictName")
    var DistrictName: String = ""

    @Element(name = "BlockCode")
    var BlockCode: String = ""

    @Element(name = "BlockName")
    var BlockName: String = ""

    @Element(name = "PanchayatCode")
    var PanchayatCode: String = ""

    @Element(name = "PanchayatName")
    var PanchayatName: String = ""

    @Element(name = "VillageCode")
    var VillageCode: String = ""

    @Element(name = "VillageName")
    var VillageName: String = ""

    @Element(name = "MobileNumber")
    var MobileNumber: String = ""

    @Element(name = "DistrictCode_LG")
    var DistrictCode_LG: String = ""

    @Element(name = "BlockCode_LG")
    var BlockCode_LG: String = ""

    @Element(name = "VillageCode_LG")
    var VillageCode_LG: String = ""

    @Element(name = "DOB")
    var DOB: String = ""

    @Element(name = "AccountNo")
    var AccountNo: String = ""

    @Element(name = "IFSCCODE")
    var IFSCCODE: String = ""

    @Element(name = "BankName")
    var BankName: String = ""

    @Element(name = "Gender")
    var Gender: String = ""

    @Element(name = "Farmertype")
    var Farmertype: String = ""

    @Element(name = "PanchayatCode_LG")
    var PanchayatCode_LG: String = ""
}

@Root(strict = false)
class ArrayOfDBTFarmerRegistration {
    @ElementList(name = "DBTFarmerRegistration", inline = true, required = false)
    var DBTFarmerRegistration: Array<DBTFarmerRegistration>? = null
}

class Hero {
    var name: String = ""
    var realname: String = ""
    var team: String = ""
    var firstappearance: String = ""
    var createdby: String = ""
    var publisher: String = ""
    var imageurl: String = ""
    var bio: String = ""
}

class Destination {
    var id: Int = 0
    var city: String = ""
    var description: String = ""
    var country: String = ""
}


public open class Product(
    val id: Int,
    val title: String,
    val shortdesc: String,
    val rating: Double,
    val price: Int,
    val image: Int

)


public data class FarmerAppRegistrationDetails(

    @SerializedName("page")
    val registrationNo: String?,
    val mobileNo: String?,
    val farmerName: String?,
    val OTP: String?,
    val deviceRegId: String?,
    val registrationFlag: String?,
    val appRegistrationDateTime: String?,
    val currentLocation: String?,
    val imeiNo: String?,
    val entryDate: String?

)

public data class FarmerRegistrationDetailsModel(
    val Registration_ID: String,
    var AadhaarNumber: String,
    var Farmer_FName: String,
    var Farmer_LName: String,
    var Father_Husband_Name: String?,
    var DOB: String,
    var Gender: String,
    var CastCateogary: String,
    var Farmertype: String,
    var DistrictCode: String,
    var BlockNameHN: String,
    var DistNameHN: String,
    var BlockCode: String,
    var PanchayatCode: String,
    var VillName: String?,
    var VillageCode: String?,
    var MobileNumber: String,
    var Confirm_Aadhaar: String?
)