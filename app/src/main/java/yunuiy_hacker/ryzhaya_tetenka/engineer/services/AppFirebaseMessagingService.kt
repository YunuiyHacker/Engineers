package yunuiy_hacker.ryzhaya_tetenka.engineer.services

import android.annotation.SuppressLint
import android.app.Application
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.NOTIFICATION_ID
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.notification.NotificationUtil

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class AppFirebaseMessagingService :
    FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationBuilder =
            NotificationUtil(application).createMessage(
                title = message.notification?.title ?: "",
                content = message.notification?.body ?: ""
            )
        notificationBuilder.build()

        with(NotificationManagerCompat.from(application)) {
            notify(NOTIFICATION_ID.toInt(), notificationBuilder.build())
        }


    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }
}