package id.jayaantara.adminrupabali.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

const val AUTH_DATA_STORE_NAME = "auth_data_store"

val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_DATA_STORE_NAME)