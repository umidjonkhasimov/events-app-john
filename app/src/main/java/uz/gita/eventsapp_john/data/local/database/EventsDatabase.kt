package uz.gita.eventsapp_john.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.eventsapp_john.data.local.dao.EventsDao
import uz.gita.eventsapp_john.data.local.entity.EventsEntity

@Database(entities = [EventsEntity::class], version = 1, exportSchema = false)
abstract class EventsDatabase : RoomDatabase() {
    abstract fun getDao(): EventsDao
}