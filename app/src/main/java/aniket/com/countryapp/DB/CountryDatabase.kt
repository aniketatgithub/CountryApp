package aniket.com.countryapp.DB

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import aniket.com.countryapp.Model.CountryModel

@Database(entities = [(CountryModel::class)], version = 1)
abstract class CountryDatabase :RoomDatabase(){

    abstract fun countryDao() : CountryDao
}


