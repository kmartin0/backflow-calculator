package com.km.backflow.calculator.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubAsset(
    @SerializedName("name")
    val name: String,

    @SerializedName("content_type")
    val contentType: String,

    @SerializedName("browser_download_url")
    val browserDownloadUrl: String,
) : Parcelable
