package uz.gita.eventsapp_john.domain.repository.impl

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.net.wifi.WifiManager
import android.util.Log
import uz.gita.eventsapp_john.R
import uz.gita.eventsapp_john.data.local.dao.EventsDao
import uz.gita.eventsapp_john.data.local.entity.EventsEntity
import uz.gita.eventsapp_john.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl
@Inject constructor(
    private val eventsDao: EventsDao
) : AppRepository {
    override suspend fun getAllDisableEvents(): List<EventsEntity> {
        if (!eventsDao.isInitialized()) {
            eventsDao.insertInitializedEvents(setInitialized())
        }
        return eventsDao.getAllDisabledEvents()
    }

    override suspend fun getAllEnableEvents(): List<EventsEntity> {
        if (!eventsDao.isInitialized()) {
            eventsDao.insertInitializedEvents(setInitialized())
        }
        return eventsDao.getAllEnabledEvents()
    }

    override suspend fun updateEventStateToDisable(eventId: Int) {
        eventsDao.disableEvent(eventId)
    }

    override suspend fun updateEventStateToEnable(eventId: Int) {
        eventsDao.enableEvent(eventId)
    }

    private fun setInitialized(): List<EventsEntity> {
        return listOf(
            EventsEntity(
                id = 1,
                eventIcon = R.drawable.ic_screen_on,
                eventName = R.string.screen_on,
                events = Intent.ACTION_SCREEN_ON
            ),

            EventsEntity(
                id = 2,
                eventIcon = R.drawable.ic_screen_off,
                eventName = R.string.screen_off,
                events = Intent.ACTION_SCREEN_OFF
            ),

            EventsEntity(
                id = 3,
                eventIcon = R.drawable.ic_wifi_on,
                eventName = R.string.wifi_on,
                events = WifiManager.WIFI_STATE_CHANGED_ACTION,
//                eventState = WifiManager.WIFI_STATE_ENABLED
            ),

            EventsEntity(
                id = 4,
                eventIcon = R.drawable.ic_wifi_off,
                eventName = R.string.wifi_off,
                events = WifiManager.WIFI_STATE_CHANGED_ACTION,
//                eventState = WifiManager.WIFI_STATE_DISABLED
            ),

            EventsEntity(
                id = 5,
                eventIcon = R.drawable.ic_bluetooth_on,
                eventName = R.string.bluetooth_on,
                events = BluetoothAdapter.ACTION_STATE_CHANGED,
//                eventState = BluetoothAdapter.STATE_CONNECTED
            ),

            EventsEntity(
                id = 6,
                eventIcon = R.drawable.ic_bluetooth_off,
                eventName = R.string.bluetooth_off,
                events = BluetoothAdapter.ACTION_STATE_CHANGED,
//                eventState = BluetoothAdapter.STATE_DISCONNECTED
            ),

            EventsEntity(
                id = 7,
                eventIcon = R.drawable.ic_headphones_on,
                eventName = R.string.headphones_on,
                events = Intent.ACTION_HEADSET_PLUG,
//                eventState = 1
            ),

            EventsEntity(
                id = 8,
                eventIcon = R.drawable.ic_headphones_off,
                eventName = R.string.headphones_off,
                events = Intent.ACTION_HEADSET_PLUG,
//                eventState = 0
            ),

            EventsEntity(
                id = 9,
                eventIcon = R.drawable.ic_airplane_on,
                eventName = R.string.airplane_on,
                events = Intent.ACTION_AIRPLANE_MODE_CHANGED,
            ),

            EventsEntity(
                id = 10,
                eventIcon = R.drawable.ic_airplane_off,
                eventName = R.string.airplane_off,
                events = Intent.ACTION_AIRPLANE_MODE_CHANGED,
            ),

            EventsEntity(
                id = 11,
                eventIcon = R.drawable.ic_power_on,
                eventName = R.string.battery_charging_on,
                events = Intent.ACTION_POWER_CONNECTED
            ),

            EventsEntity(
                id = 12,
                eventIcon = R.drawable.ic_power_off,
                eventName = R.string.battery_charging_off,
                events = Intent.ACTION_POWER_DISCONNECTED
            ),

            EventsEntity(
                id = 13,
                eventIcon = R.drawable.ic_timezone_changed,
                eventName = R.string.timezone_changed,
                events = Intent.ACTION_TIMEZONE_CHANGED
            ),

            EventsEntity(
                id = 14,
                eventIcon = R.drawable.ic_battery_low,
                eventName = R.string.text_battery_low,
                events = Intent.ACTION_BATTERY_LOW
            ),

            EventsEntity(
                id = 15,
                eventIcon = R.drawable.ic_battery_full,
                eventName = R.string.battery_full,
                events = Intent.ACTION_BATTERY_CHANGED
            ),
        )
    }
}