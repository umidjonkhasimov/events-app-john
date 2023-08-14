package uz.gita.eventsapp_john.utils.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import uz.gita.eventsapp_john.MainActivity
import uz.gita.eventsapp_john.R
import uz.gita.eventsapp_john.utils.ENABLED_ACTIONS
import uz.gita.eventsapp_john.utils.STOP_INTENT
import uz.gita.eventsapp_john.utils.broadcast.EventReceiver
import uz.gita.eventsapp_john.utils.notification.createChannel
import uz.gita.eventsapp_john.utils.notification.createNotification

class EventsService : Service() {
    private val receiver = EventReceiver()

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        startEventsService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return if (intent?.extras?.getString(STOP_INTENT) != STOP_INTENT) {
            val events = intent?.extras?.getStringArrayList(ENABLED_ACTIONS)

            registerReceiver(receiver, IntentFilter().apply {
                for (i in events?.indices!!) {
                    addAction(events[i])
                }
            })

            START_NOT_STICKY
        } else {
            stopSelf()
            START_NOT_STICKY
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        receiver.clearReceiver()
        unregisterReceiver(receiver)
    }

    private fun startEventsService() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        startForeground(1, createNotification(this, pendingIntent, createRemoteView()))
    }

    private fun createRemoteView(): RemoteViews {
        val view = RemoteViews(packageName, R.layout.remote_view)
        view.setOnClickPendingIntent(R.id.closeButton, createPendingIntent())
        return view
    }

    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(this, EventsService::class.java).apply {
            putExtra(STOP_INTENT, STOP_INTENT)
        }

        return PendingIntent.getService(
            this,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
    }
}