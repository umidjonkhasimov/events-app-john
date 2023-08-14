package uz.gita.eventsapp_john.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.gita.eventsapp_john.data.local.entity.EventsEntity

@Dao
interface EventsDao {
    @Query("SELECT EXISTS (SELECT * FROM events)")
    fun isInitialized(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInitializedEvents(events: List<EventsEntity>)

    @Query("SELECT * FROM events WHERE eventState = 0")
    fun getAllDisabledEvents(): List<EventsEntity>

    @Query("SELECT * FROM events WHERE eventState = 1")
    fun getAllEnabledEvents(): List<EventsEntity>

    @Query("UPDATE events SET eventState = 0 WHERE id = :eventId")
    fun disableEvent(eventId: Int)

    @Query("UPDATE events SET eventState = 1 WHERE id = :eventId")
    fun enableEvent(eventId: Int)
}