package com.ejlim.data.di

import android.content.Context
import androidx.room.Room
import com.ejlim.data.BuildConfig
import com.ejlim.data.database.AppDatabase
import com.ejlim.data.datasource.FacilityDataSource
import com.ejlim.data.repository.FacilityRepository
import com.ejlim.data.service.FacilityService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_db"
        ).build()

    @Provides
    fun provideFacilityDao(appDatabase: AppDatabase) = appDatabase.facilityDao()

    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): FacilityService {
        return retrofit.create(FacilityService::class.java)
    }

    @Provides
    fun provideFacilityRepository(facilityDatasource: FacilityDataSource, database: AppDatabase)
        = FacilityRepository(facilityDatasource, database)

    @Provides
    fun provideFacilityDatasource(apiService: FacilityService) = FacilityDataSource(apiService)
}