package yunuiy_hacker.ryzhaya_tetenka.engineer.utils.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BADGE_ICON_LARGE
import yunuiy_hacker.ryzhaya_tetenka.engineer.R
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.NOTIFICATION_CHANNEL_ID

class NotificationUtil(private val application: Application) {

    fun createMessage(
        title: String,
        content: String
    ): NotificationCompat.Builder {
        createNotificationChannel()

        return NotificationCompat.Builder(application, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_logo)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            application.getString(R.string.notification_channel_description),
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.enableLights(false)
        notificationChannel.enableVibration(false)

        val notificationManager =
            application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}