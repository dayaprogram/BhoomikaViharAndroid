package com.example.dbtagri.RemoteDataRepository

import android.content.Context
import android.content.SharedPreferences
import com.bseb.crossword.RemoteDataRepository.ApiService
import com.bhoomikabihar.surveyapp.Model.User
import com.bhoomikavihar.surveyapp.R
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Constants {
    const val BASE_URL = "http://164.100.130.206/WebService/"
    const val LOGIN_URL = "Token"
    const val POSTS_URL = "posts"
}

//public class RetrofitService {
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl("https://newsapi.org/v2/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//}

class ApiClient {
    private lateinit var apiService: ApiService

    fun getApiService(context: Context): ApiService {

        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .client(okhttpClient(context)) // Add our Okhttp client
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService
    }

    /**
     * Initialize OkhttpClient with our interceptor
     */
    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }
}

class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}


class SessionManager(context: Context) {

    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    // context.getSharedPreferences( "DBTAgriVerify", Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val AC_UserId = "AC_UserId"
        const val AC_Name = "AC_Name"
        const val AC_Mobile = "AC_Mobile"
        const val userRole = "userRole"
        const val AC_Email = "AC_Email"
        const val AC_Gender = "AC_Gender"
        const val distId = "distId"
        const val blockId = "blockId"
        const val panchayatId = "panchayatId"

    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }


    fun clearToken() {
        val editor = prefs.edit()
        editor.putString(AC_Name, "")
        editor.putString(AC_Mobile, "")
        editor.putString(AC_Email, "")
        editor.putString(AC_Gender, "")
        editor.clear()
        editor.apply()
    }


    fun saveAuthDetails(loginResponse: User) {
        val editor = prefs.edit()
        editor.putString(AC_Name, loginResponse.name)
        editor.putString(AC_Mobile, loginResponse.mobileNo)
        editor.putString(AC_Email, loginResponse.emailId)
        editor.putString(AC_Gender, loginResponse.gender)
        editor.apply()
    }

    fun fetchAuthACDetails(): User {
        var user: User = User(
            prefs.getString(AC_Name, "")!!,
            prefs.getString(AC_Mobile, "")!!,
            prefs.getString(AC_Email, "")!!,
            prefs.getString(AC_Gender, "")!!,
            prefs.getString(userRole, "")!!,
        )
        return user
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun saveOTPDetails(otp: String) {
        val editor = prefs.edit()
        editor.putString("otp", otp)
        editor.apply()
    }

    fun fetchOTPDetails(): String? {
        return prefs.getString("otp", "")
    }
}

