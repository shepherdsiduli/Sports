package co.shepherd.sports.di

import android.content.Context
import androidx.room.Room
import co.shepherd.sports.db.SportsDatabase
import co.shepherd.sports.db.dao.EventsDao
import co.shepherd.sports.db.dao.ScheduleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SportsDatabase =
        Room.databaseBuilder(
            context,
            SportsDatabase::class.java,
            "SportsApp-DB"
        ).build()

    @Provides
    @Singleton
    fun provideWeatherForecastDao(database: SportsDatabase): EventsDao =
        database.eventsDao()

    @Provides
    @Singleton
    fun provideCurrentWeatherDao(database: SportsDatabase): ScheduleDao =
        database.scheduleDao()
}