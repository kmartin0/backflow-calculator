package com.km.backflow.calculator.api

import com.km.backflow.calculator.models.GithubRelease
import retrofit2.http.GET

interface GithubApiService {

    @GET("repos/kmartin0/backflow-calculator/releases/latest")
    suspend fun getLatestGithubRelease(): GithubRelease

}