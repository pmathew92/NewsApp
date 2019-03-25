package com.prince.newsapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.prince.newsapp.BuildConfig
import com.prince.newsapp.data.local.NewsDao
import com.prince.newsapp.data.local.NewsDatabase
import com.prince.newsapp.data.remote.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule {
    @Singleton
    @Provides
    @Named("Application Context")
    fun provideAppContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.HEADERS
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder().addInterceptor { chain ->
            var request = chain.request()
            val builder1 = request.newBuilder()
                .addHeader("Content-Type", "application/json")

            request = builder1.build()

            chain.proceed(request)
        }.addInterceptor(interceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideDatabase(@Named("Application Context") context: Context): NewsDatabase {
        return Room
            .databaseBuilder(
                context,
                NewsDatabase::class.java,
                "NewSDB"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideStoryDao(dataBase: NewsDatabase): NewsDao {
        return dataBase.newsDao()
    }
}