package uz.gita.eventsapp_john.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.eventsapp_john.data.local.dao.EventsDao
import uz.gita.eventsapp_john.data.local.database.EventsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EventsDatabaseModule {

    @Provides
    @Singleton
    fun provideEventsDatabase(@ApplicationContext context: Context): EventsDatabase =
        Room
            .databaseBuilder(context, EventsDatabase::class.java, "events.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(db: EventsDatabase): EventsDao = db.getDao()
}