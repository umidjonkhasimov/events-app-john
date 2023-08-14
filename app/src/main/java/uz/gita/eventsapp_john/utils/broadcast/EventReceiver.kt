package uz.gita.eventsapp_john.utils.broadcast

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.wifi.WifiManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.eventsapp_john.R

@AndroidEntryPoint
class EventReceiver : BroadcastReceiver() {
    private lateinit var mediaPlayer: MediaPlayer
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onReceive(context: Context?, intent: Intent?) {

        scope.launch {
            when (intent?.action) {
                Intent.ACTION_SCREEN_ON -> {
                    mediaPlayer = MediaPlayer.create(context, R.raw.screen_on)
                    mediaPlayer.start()
                }

                Intent.ACTION_SCREEN_OFF -> {
                    mediaPlayer = MediaPlayer.create(context, R.raw.screen_off)
                    mediaPlayer.start()
                }

                WifiManager.WIFI_STATE_CHANGED_ACTION -> {
                    if (intent.getIntExtra(
                            WifiManager.EXTRA_WIFI_STATE,
                            WifiManager.WIFI_STATE_UNKNOWN
                        ) == WifiManager.WIFI_STATE_ENABLED
                    ) {
                        mediaPlayer = MediaPlayer.create(context, R.raw.wifi_on)
                        mediaPlayer.start()
                    } else if (intent.getIntExtra(
                            WifiManager.EXTRA_WIFI_STATE,
                            WifiManager.WIFI_STATE_UNKNOWN
                        ) == WifiManager.WIFI_STATE_DISABLED
                    ) {
                        mediaPlayer = MediaPlayer.create(context, R.raw.wifi_off)
                        mediaPlayer.start()
                    }
                }

                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR) == BluetoothAdapter.STATE_ON) {
                        delay(2000)
                        mediaPlayer = MediaPlayer.create(context, R.raw.bluetooth_on)
                        mediaPlayer.start()
                    } else {
                        delay(2000)
                        mediaPlayer = MediaPlayer.create(context, R.raw.bluetooth_off)
                        mediaPlayer.start()
                    }
                }

                Intent.ACTION_HEADSET_PLUG -> {
                    if (intent.getIntExtra("state", -1) == 1) {
                        mediaPlayer = MediaPlayer.create(context, R.raw.headphone_on)
                        mediaPlayer.start()
                    } else {
                        mediaPlayer = MediaPlayer.create(context, R.raw.bluetooth_off)
                        mediaPlayer.start()
                    }
                }

                Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                    if (intent.getBooleanExtra("state", false)) {
                        mediaPlayer = MediaPlayer.create(context, R.raw.airplane_on)
                        mediaPlayer.start()
                    } else {
                        mediaPlayer = MediaPlayer.create(context, R.raw.airplane_off)
                        mediaPlayer.start()
                    }
                }

                Intent.ACTION_POWER_CONNECTED -> {
                    mediaPlayer = MediaPlayer.create(context, R.raw.power_connected)
                    mediaPlayer.start()
                }

                Intent.ACTION_POWER_DISCONNECTED -> {
                    mediaPlayer = MediaPlayer.create(context, R.raw.power_disconnected)
                    mediaPlayer.start()
                }

                Intent.ACTION_TIMEZONE_CHANGED -> {
                    mediaPlayer = MediaPlayer.create(context, R.raw.timezone_changed)
                    mediaPlayer.start()
                }

                Intent.ACTION_BATTERY_LOW -> {
                    mediaPlayer = MediaPlayer.create(context, R.raw.battery_low)
                    mediaPlayer.start()
                }

                Intent.ACTION_BATTERY_CHANGED -> {
                    if (intent.getIntExtra("level", 0) == 100) {
                        mediaPlayer = MediaPlayer.create(context, R.raw.battery_full)
                        mediaPlayer.start()
                    }
                }
            }
        }
    }

    fun clearReceiver() {
        scope.cancel()
    }
}