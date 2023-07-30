package id.jayaantara.adminrupabali.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.jayaantara.adminrupabali.BuildConfig
import id.jayaantara.adminrupabali.BuildConfig.BASE_URL
import id.jayaantara.adminrupabali.common.authDataStore
import id.jayaantara.adminrupabali.data.remote.AdminRupaBaliApi
import id.jayaantara.adminrupabali.data.repository.AdminRupaBaliRepositoryImpl
import id.jayaantara.adminrupabali.domain.repository.AdminRupaBaliRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): AdminRupaBaliApi {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AdminRupaBaliApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDataStorePreference(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.authDataStore
    }

    @Provides
    @Singleton
    fun provideAdminRupaBaliRepository(api: AdminRupaBaliApi, dataStore: DataStore<Preferences>): AdminRupaBaliRepository {
        return AdminRupaBaliRepositoryImpl(api, dataStore)
    }
}