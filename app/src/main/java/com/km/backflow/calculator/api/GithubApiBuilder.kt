package com.km.backflow.calculator.api

import com.km.backflow.calculator.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubApiBuilder {

        private const val BASE_GITHUB_API_URL = "https://api.github.com/"

        /**
         * @return [GithubApiService] The service class off the retrofit client.
         */
        fun createApi(): GithubApiService {
            // Create the Retrofit instance
            val githubApi = Retrofit.Builder()
                .baseUrl(BASE_GITHUB_API_URL)
                .client(getOkHttpClientApi())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Return the Retrofit NumbersApiService
            return githubApi.create(GithubApiService::class.java)
        }

        private fun getOkHttpClientApi(): OkHttpClient {
            val okHttpClientBuilder = OkHttpClient.Builder()

            if (BuildConfig.DEBUG) {
                okHttpClientBuilder.addInterceptor(
                    HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
                )
            }

            return okHttpClientBuilder.build()
        }

}