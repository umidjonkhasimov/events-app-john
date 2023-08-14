package uz.gita.eventsapp_john.utils.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import uz.gita.eventsapp_john.R
import uz.gita.eventsapp_john.utils.CHANNEL_ID
import uz.gita.eventsapp_john.utils.CHANNEL_NAME

fun createChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)

        val notification = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notification.createNotificationChannel(channel)
    }
}

fun createNotification(context: Context, pendingIntent: PendingIntent, remoteViews: RemoteViews) = NotificationCompat
    .Builder(context, CHANNEL_ID)
    .setContentIntent(pendingIntent)
    .setSmallIcon(R.drawable.ic_bell)
    .setContentTitle("Event")
    .setCustomContentView(remoteViews)
    .setOngoing(true)
    .build()