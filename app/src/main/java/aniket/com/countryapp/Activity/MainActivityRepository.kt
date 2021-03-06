package aniket.com.countryapp.Activity

import android.arch.lifecycle.LiveData
import android.util.Log
import aniket.com.countryapp.CountryApplication
import aniket.com.countryapp.Model.CountryModel
import aniket.com.countryapp.Retrofit.NetworkCall
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityRepository {

    var lastResponse: List<CountryModel>? = null
    val BASE_URL = "https://6119f2efcbf1b30017eb5404.mockapi.io/"
    val TAG = MainActivityRepository::class.java.simpleName

    fun getCountries(): LiveData<List<CountryModel>> {
        return CountryApplication.database!!.countryDao().getAllCountries()
    }

    fun ApiCallAndPutInDB() {
        val gson = Gson()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()
        val restApi = retrofit.create<NetworkCall>(NetworkCall::class.java)
        restApi.getAllCountries().enqueue(object : Callback<List<CountryModel>> {
            override fun onFailure(call: Call<List<CountryModel>>?, t: Throwable?) {
                Log.e(TAG, "OOPS!! something went wrong..")
            }

            override fun onResponse(
                call: Call<List<CountryModel>>?,
                response: Response<List<CountryModel>>?
            ) {
                when (response!!.code()) {
                    200 -> {
                        if (lastResponse == null || lastResponse != response.body()!!) {
                            lastResponse = response.body()
                            Log.d("lastdata", "last data is :   $lastResponse")
                            Thread({
                                Log.d("called", "REFRESHED DATA")
                                CountryApplication.database!!.countryDao().deleteAllCountries()
                                CountryApplication.database!!.countryDao()
                                    .insertAllCountries(response.body()!!)
                            }).start()
                        } else  {
                            Log.d("called", "Not Refresing , SAME DATA")
                                }
                            } } } }) }
}



