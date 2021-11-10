package com.km.backflow.calculator.helpers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.km.backflow.calculator.R
import com.km.backflow.calculator.models.GithubAsset

class NotificationBuilder {

    companion object {

        private const val CHANNEL_ID = "km_backflow_calculator_channel_id"
        private const val NOTIFICATION_ID = 0

        /**
         * Displays a notification for a [GithubAsset]. Clicking on it will open the asset download url
         */
        fun buildAppUpdateNotification(context: Context, asset: GithubAsset, version: String) {
            // Create the notification channel for this notification.
            createNotificationChannel(context)


            // Builder obj with notification info.
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(context.getString(R.string.app_update_title))
                .setContentText(context.getString(R.string.app_update_description, version))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(createPendingIntent(context, asset))
                .setAutoCancel(true)

            // Display the notification.
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
        }

        /**
         * Create a notification channel to assign with a notification.
         */
        private fun createNotificationChannel(context: Context) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name: String = context.getString(R.string.app_update_title)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance)
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }

        /**
         * Create a pending intent for a message type
         *
         * @param context
         * @return PendingIntent with action determined by message type.
         */
        private fun createPendingIntent(context: Context, asset: GithubAsset): PendingIntent {

            val intentUri = Uri.parse(asset.browserDownloadUrl)
            val githubIntent = Intent(Intent.ACTION_VIEW, intentUri)

            // Return pending intent which starts google maps with directions.
            return PendingIntent.getActivity(
                context,
                1,
                githubIntent,
                0
            )
        }
    }
}