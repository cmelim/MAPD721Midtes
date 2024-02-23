package com.camilo.test

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

private val Context.dataStore by preferencesDataStore(name = "city_preferences")
class CityDataStore (context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    val cityData: Flow<CityData> = dataStore.data.map { preferences ->
        CityData(
            city = preferences[CITY_NAME_KEY] ?: "",
            date = preferences[CITY_DATE_KEY] ?: "",
            temperature = preferences[CITY_TEMPERATURE_KEY] ?: "",
            description = preferences[CITY_DESCRIPTRION_KEY] ?: ""
        )
    }
    val getSavedData: Flow<String> = context.dataStore.data.map { preferences ->
        val cityName = preferences[CITY_NAME_KEY] ?: ""
        val cityDate = preferences[CITY_DATE_KEY] ?: ""
        val cityTemp = preferences[CITY_TEMPERATURE_KEY] ?: ""
        val cityDes = preferences[CITY_DESCRIPTRION_KEY] ?: ""
        "\n City Name: $cityName, Date: $cityDate, Temperature: $cityTemp, Description: $cityDes"
    }.onStart { emit("") }

    suspend fun saveCityData(cityName: String, cityDate: String, cityTemp: String,cityDes: String) {
        dataStore.edit { preferences ->
            preferences[CITY_NAME_KEY] = cityName
            preferences[CITY_DATE_KEY] = cityDate
            preferences[CITY_TEMPERATURE_KEY] = cityTemp
            preferences[CITY_DESCRIPTRION_KEY] = cityDes
        }
    }

    suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(CITY_NAME_KEY)
            preferences.remove(CITY_DATE_KEY)
            preferences.remove(CITY_TEMPERATURE_KEY)
            preferences.remove(CITY_DESCRIPTRION_KEY)
            //preferences.remove(USER_ID_KEY)
        }
    }

    companion object {
        private val CITY_NAME_KEY = stringPreferencesKey("city_name")
        private val CITY_DATE_KEY = stringPreferencesKey("city_date")
       // private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val CITY_TEMPERATURE_KEY = stringPreferencesKey("city_temperature")
        private val CITY_DESCRIPTRION_KEY = stringPreferencesKey("city_description")
    }
}

data class CityData(
    val city: String,
    val date: String,
    val temperature: String,
    val description: String
)
