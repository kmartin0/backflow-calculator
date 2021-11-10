package com.km.backflow.calculator.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class GithubRelease(
    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("tag_name")
    val tagName: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("published_at")
    val publishedAt: String,

    @SerializedName("assets")
    val assets: List<GithubAsset>,
) : Parcelable