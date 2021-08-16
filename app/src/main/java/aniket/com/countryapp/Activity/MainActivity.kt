package aniket.com.countryapp.Activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import aniket.com.countryapp.ConnectivityLiveData
import aniket.com.countryapp.CountryRecyclerViewAdapter
import aniket.com.countryapp.Model.CountryModel
import aniket.com.countryapp.R

class MainActivity : AppCompatActivity() {
    private lateinit var connectivityLiveData: ConnectivityLiveData
    lateinit var countryRecyclerView: RecyclerView
    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        countryRecyclerView = findViewById(R.id.countryRecyclerView)
        connectivityLiveData = ConnectivityLiveData(application)

        connectivityLiveData.observe(this, Observer { isAvailable ->
            when (isAvailable) {
                true -> {
                  networkcall()

                }
                false -> Toast.makeText(
                    this,
                    "No internet showing data from DB",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })





        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.getAllCountryList()
            .observe(this, Observer<List<CountryModel>> { countryList ->
                Log.e(MainActivity::class.java.simpleName, countryList.toString())
                setUpCountryRecyclerView(countryList!!)
            })
    }

    private fun networkcall() {
        mainActivityViewModel.getCountriesFromAPIAndStore()
    }

    fun setUpCountryRecyclerView(countries: List<CountryModel>) {
        val countryRecyclerViewAdapter = CountryRecyclerViewAdapter(this, countries)
        countryRecyclerView.adapter = countryRecyclerViewAdapter
        countryRecyclerView.layoutManager = GridLayoutManager(this, 1)
        countryRecyclerView.setHasFixedSize(true)

    }
}
