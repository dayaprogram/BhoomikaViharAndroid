package com.example.dbtagri.LocalDataRepository.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
public interface LocalDao {
    @Query("SELECT * FROM InputSubsidyApplicationLocal")
    fun getAllInputSubsidyApplicationLocal(): List<InputSubsidyApplicationLocal>

    @Insert
    fun insertAllInputSubsidyApplicationLocal(farmers: List<InputSubsidyApplicationLocal>)

    @Insert
    fun insertAllInputSubsidyLandLocal(farmersLand: List<InputSubsidyLandLocal>)

    @Query("SELECT * FROM InputSubsidyApplicationLocal WHERE ACStatus=1 LIMIT 1")
    fun getInputSubsidyApprovedFarmerDetailsTop1(): InputSubsidyApplicationLocal

    @Query("SELECT count(RegistrationNo)  FROM InputSubsidyApplicationLocal WHERE FinalSubmitFlag=1")
    fun getInputSubActionToUploadCount(): LiveData<Int>

    @Query("SELECT count(RegistrationNo)  FROM InputSubsidyApplicationLocal WHERE ACStatus=1 and FinalSubmitFlag=1")
    fun getInputSubsidyApprovedFarmerCount(): LiveData<Int>


    @Query("SELECT count(RegistrationNo)  FROM InputSubsidyApplicationLocal WHERE ACStatus=2 and FinalSubmitFlag=1")
    fun getInputSubsidyRejectFarmerCount(): LiveData<Int>

    @Query("SELECT count(RegistrationNo)  FROM InputSubsidyApplicationLocal WHERE FinalSubmitFlag==0")
    fun getInputSubsidyUnApprovedFarmerCount(): LiveData<Int>

    @Query("SELECT count(RegistrationNo)  FROM InputSubsidyApplicationLocal WHERE FinalSubmitFlag!=3")
    fun getInputSubUnUploadCount(): LiveData<Int>

    @Query("SELECT * FROM InputSubsidyApplicationLocal WHERE FinalSubmitFlag==0 ")
    fun getInputSubsidyUnApprovedFarmerList(): LiveData<List<InputSubsidyApplicationLocal>>

    @Query("SELECT * FROM InputSubsidyLandLocal WHERE RegistrationNo = :registrationNo")
    fun getInputSubsidyUnApprovedFarmerLandDetails(registrationNo: String): LiveData<List<InputSubsidyLandLocal>>

    @Query("UPDATE InputSubsidyApplicationLocal SET aCStatus = :aCStatus,aCRemarks=:aCRemarks, totalApprovedLand=:totalApprovedLand,totalApprovedAmt=:totalApprovedAmt,photoLatitude=:photoLatitude,photoLongitude=:photoLongitude,FarmerName1=:FarmerName1,FarmerMobile1=:FarmerMobile1,FarmerName2=:FarmerName2,FarmerMobile2=:FarmerMobile2 WHERE RegistrationNo =:RegistrationNo AND ApplicationID=:ApplicationID")
    fun updateInputSubsidyApplicationLocal(
        aCStatus: Int?,
        aCRemarks: String,
        totalApprovedLand: Double,
        totalApprovedAmt: Double,
        photoLatitude: Double,
        photoLongitude: Double,
        FarmerName1: String,
        FarmerMobile1: String,
        FarmerName2: String,
        FarmerMobile2: String,
        RegistrationNo: String,
        ApplicationID: String
    )


    @Query("UPDATE InputSubsidyApplicationLocal SET imeiNo=:IMEINumber, aCStatus = :aCStatus,aCRemarks=:aCRemarks,ACReason=:ACReason,SoilTest=:soiltest,photoLatitude=:photoLatitude,photoLongitude=:photoLongitude WHERE RegistrationNo =:RegistrationNo AND ApplicationID=:ApplicationID")
    fun updateInputSubsidyAction(
        aCStatus: Int?,
        aCRemarks: String,
        ACReason: String,
        RegistrationNo: String,
        ApplicationID: String,
        soiltest: String,
        photoLatitude: Double,
        photoLongitude: Double,
        IMEINumber: String,
    )

    @Query("UPDATE InputSubsidyApplicationLocal SET imeiNo=:IMEINumber,aCStatus = :aCStatus,aCRemarks=:aCRemarks,ACReason=:ACReason, SoilTest=:SoilTest,photoLatitude=:photoLatitude,photoLongitude=:photoLongitude,FinalSubmitFlag=1,actionTakenDtTime=:ActionTakenDtTime WHERE RegistrationNo =:RegistrationNo AND ApplicationID=:ApplicationID")
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
    )

    @Query("UPDATE InputSubsidyApplicationLocal SET totalApprovedLand=:totalApprovedLand,totalApprovedAmt=:totalApprovedAmt WHERE RegistrationNo =:RegistrationNo AND ApplicationID=:ApplicationID")
    fun updateInputSubsidyLandApproval(
        totalApprovedLand: Double,
        totalApprovedAmt: Double,
        RegistrationNo: String,
        ApplicationID: String
    )

    @Query("UPDATE InputSubsidyApplicationLocal SET photoPath=:photoPath,ActionTakenDtTime=:ActionTakenDtTime WHERE RegistrationNo =:RegistrationNo AND ApplicationID=:ApplicationID")
    fun updateInputSubsidyPicturedetails(

        photoPath: String,
        ActionTakenDtTime: String,

        RegistrationNo: String,
        ApplicationID: String
    )

    @Query("UPDATE InputSubsidyApplicationLocal SET FarmerName1=:FarmerName1,FarmerMobile1=:FarmerMobile1,FarmerName2=:FarmerName2,FarmerMobile2=:FarmerMobile2 ,FinalSubmitFlag=1 WHERE RegistrationNo =:RegistrationNo AND ApplicationID=:ApplicationID")
    fun updateInputSubsidyFarmerDetailsFinal(
        FarmerName1: String,
        FarmerMobile1: String,
        FarmerName2: String,
        FarmerMobile2: String,
        RegistrationNo: String,
        ApplicationID: String
    )

    @Query("SELECT Count(RegistrationNo) from InputSubsidyApplicationLocal WHERE RegistrationNo =:RegistrationNo AND ApplicationID=:ApplicationID and  FinalSubmitFlag=1")
    fun getInputSubsidyApplicationStatus(
        RegistrationNo: String,
        ApplicationID: String
    ): LiveData<Int>

    //    @Query("SELECT * FROM InputSubsidyApplicationLocal WHERE FinalSubmitFlag==1 limit 1; ")
    @Query("SELECT * FROM InputSubsidyApplicationLocal WHERE FinalSubmitFlag==1 and aCStatus!=0 order by ActionTakenDtTime; ")
    fun getInputSubsidyApprovedFarmer(): LiveData<List<InputSubsidyApplicationLocal>>


    @Query("DELETE FROM InputSubsidyApplicationLocal WHERE RegistrationNo =:RegistrationNo AND ApplicationID=:ApplicationID and FinalSubmitFlag==1")
    fun uploadMarkInputSubsidyApprovedFarmer(
        RegistrationNo: String,
        ApplicationID: String
    )

    @Query("DELETE FROM InputSubsidyApplicationLocal")
    fun deleteInputSubsidyData()

//    @Insert
//    fun insertAll(vararg users: User)
//
//    @Delete
//    fun delete(user: User)
//
//    @Query("SELECT * from user ORDER BY uid ASC")
//    fun getAlphabetizedWords(): LiveData<List<User>>

//
//    @Delete
//    fun deleteFarmerAppRegistration(farmerAppRegistration: FarmerAppRegistration)
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun updateFarmerAppRegistration(farmerAppRegistration: FarmerAppRegistration)
//
//    @Insert
//    fun insertFarmerAppRegistration(farmerAppRegistration: FarmerAppRegistration)
//
//    @Query("SELECT * FROM FarmerAppRegistration WHERE RegistrationNo = :registrationNo ORDER BY EntryDate LIMIT 1")
//    fun getFarmerAppRegistrationLocal(registrationNo: String): FarmerAppRegistration
//
//    @Query("SELECT * FROM FarmerAppRegistration  ORDER BY EntryDate LIMIT 1")
//    fun getFarmerAppRegistrationLocal(): FarmerAppRegistration
//
//    @Query("SELECT otp FROM FarmerAppRegistration WHERE RegistrationNo = :registrationNo AND otp=:otp ORDER BY EntryDate LIMIT 1")
//    fun verifyOTPFromLocal(registrationNo: String, otp: String): String
//
//
//    @Query("SELECT * FROM FarmerAppRegistration WHERE RegistrationNo = :registrationNo AND  (RegistrationFlag='COMPLETE'OR RegistrationFlag='SYNC')LIMIT 1")
//    fun getFarmerLocalDetailCompleted(registrationNo: String): LiveData<FarmerAppRegistration>?
//
//    @Query("SELECT * FROM FarmerRegistrationDetails  ORDER BY EntryDate LIMIT 1")
//    fun getFarmerRegistrationDetailsLocal(): FarmerRegistrationDetails
//
//    @Query("SELECT * FROM FarmerRegistrationDetails WHERE RegistrationNo = :registrationNo")
//    fun getFarmerRegistrationDetailsLocal(registrationNo: String): FarmerRegistrationDetails
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun updateFarmerRegistrationDetailsLocal(farmerRegistrationDetails: FarmerRegistrationDetails)
//
//    // @Insert
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun insertFarmerRegistrationDetailsLocal(farmerRegistrationDetails: FarmerRegistrationDetails)

//    @Dao
//    abstract class UserDao {
//
//        @Transaction
//        open fun updateData(users: List<User>) {
//            deleteAllUsers()
//            insertAll(users)
//        }
//        @Insert
//        abstract fun insertAll(users: List<User>)
//        @Query("DELETE FROM Users")
//        abstract fun deleteAllUsers()
//    }
}