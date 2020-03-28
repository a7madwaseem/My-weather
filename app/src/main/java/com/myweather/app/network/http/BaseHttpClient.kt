package com.myweather.app.network.http

import com.myweather.app.network.api.APIService
import com.myweather.app.network.api.ApiRoutes
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
class BaseHttpClient() : HttpClient {

    //region Member var
    private lateinit var apiService: APIService
    private lateinit var retrofit: Retrofit
    private val okHttpClient: OkHttpClient
    //endregion

    //region Init
    init {
        okHttpClient = createOkHttpClient()
        createServices(okHttpClient)
    }
    //endregion

    //region general Methods
    private fun createServices(okHttpClient: OkHttpClient) {
        apiService = createAPIService(okHttpClient)
    }
    private fun createAPIService(client: OkHttpClient): APIService {

        retrofit = Retrofit.Builder()
            .baseUrl(ApiRoutes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(APIService::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(getApiInterceptor())
            .build()
    }
    //endregion

    //region getter
    override fun getApiService(): APIService {
        return apiService
    }

    override fun getRetrofit(): Retrofit {
        return retrofit
    }

    private fun getApiInterceptor(): Interceptor {
        return Interceptor {
            val original: Request = it.request()
            val originalHttpUrl: HttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(ApiRoutes.KEY_NAME_APP_ID, ApiRoutes.API_KEY)
                .addQueryParameter(ApiRoutes.KEY_NAME_UNIT, ApiRoutes.KEY_VALUE_UNIT)
                .build()

            // Request customization: add request headers
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)

            val request: Request = requestBuilder.build()
            it.proceed(request)
        }
    }
    //endregion

}