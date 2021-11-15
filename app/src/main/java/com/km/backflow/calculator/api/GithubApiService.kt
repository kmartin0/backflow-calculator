package com.km.backflow.calculator.api

import com.km.backflow.calculator.models.GithubRelease
import retrofit2.http.GET

interface GithubApiService {

    @GET(GithubEndpoints.GET_BACKFLOW_CALCULATOR_LATEST_RELEASE)
    suspend fun getLatestGithubRelease(): GithubRelease

}