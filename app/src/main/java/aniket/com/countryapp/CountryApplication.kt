package aniket.com.countryapp

import android.app.Application
import android.arch.persistence.room.Room
import aniket.com.countryapp.DB.CountryDatabase

class CountryApplication : Application() {

    companion object {
        var database: CountryDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        database =  Room.databaseBuilder(applicationContext, CountryDatabase::class.java, "country_db").fallbackToDestructiveMigration().build()

    }
}