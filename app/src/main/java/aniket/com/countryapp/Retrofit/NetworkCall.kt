package aniket.com.countryapp.Retrofit

import retrofit2.Call
import retrofit2.http.GET
import aniket.com.countryapp.Model.CountryModel

interface NetworkCall {

    @GET("all")
    fun getAllCountries() : Call<List<CountryModel>>
}