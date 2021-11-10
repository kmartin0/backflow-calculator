package com.km.backflow.calculator.helpers

import android.content.Context
import com.km.backflow.calculator.BuildConfig
import com.km.backflow.calculator.api.GithubApiBuilder
import com.km.backflow.calculator.models.GithubRelease
import java.util.*

class NewVersionHelper {

    /**
     * Checks if the currently installed app version is the same as the latest version on github.
     * Limited to once every 24 hours.
     *
     * @param context
     */
    suspend fun checkForNewAppVersion(context: Context) {

        if (shouldCheckForUpdate(context)) {
            val api = GithubApiBuilder.createApi()

            try {
                val latestGithubRelease = api.getLatestGithubRelease()
                updatePrefLastCheckedForUpdate(context)

                if (!isInstalledApkLatestRelease(latestGithubRelease)) {
                    val apkAsset = latestGithubRelease.assets.find { asset ->
                        asset.contentType == Constants.CONTENT_TYPE_APK
                    }

                    if (apkAsset != null) {
                        NotificationBuilder.buildAppUpdateNotification(context, apkAsset, latestGithubRelease.tagName)
                    }
                }

            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Compares a Github release version with the installed app.
     *
     * @param latestRelease
     * @return Boolean true when the github release is the same as the app version.
     */
    private fun isInstalledApkLatestRelease(latestRelease: GithubRelease): Boolean {

        // Refactor the github tag so it's the same format as the app version name.
        val latestReleaseVersion = latestRelease.tagName.lowercase().replace("v", "")
        val installedVersion = BuildConfig.VERSION_NAME

        return latestReleaseVersion == installedVersion
    }

    /**
     * Checks if an app update has been checked within the last 24 hours.
     *
     * @param context
     * @return Boolean true when no check has been done within the last 24 hours.
     */
    private fun shouldCheckForUpdate(context: Context): Boolean {
        val sharedPref = SharedPreferenceHelper.getSharedPreferences(context)

        if (sharedPref == null || !sharedPref.contains(SharedPreferenceHelper.LAST_CHECKED_FOR_UPDATE_TIMESTAMP_KEY))
            return true

        // 24 hours in milliseconds.
        val checkExpiry = 86400 * 1000

        val timestampLastUpdate =
            sharedPref.getLong(SharedPreferenceHelper.LAST_CHECKED_FOR_UPDATE_TIMESTAMP_KEY, 0)

        return Date().time > timestampLastUpdate + checkExpiry
    }

    /**
     * Updates the shared preferences last checked key with the current timestamp.
     *
     * @param context
     */
    private fun updatePrefLastCheckedForUpdate(context: Context) {
        with(SharedPreferenceHelper.getSharedPreferences(context)?.edit()) {
            this?.putLong(
                SharedPreferenceHelper.LAST_CHECKED_FOR_UPDATE_TIMESTAMP_KEY,
                Date().time
            )
            this?.apply()
        }
    }

}