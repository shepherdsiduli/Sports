package co.shepherd.sports.di

import co.shepherd.sports.db.dao.EventsDao
import co.shepherd.sports.db.dao.ScheduleDao
import co.shepherd.sports.domain.ApplicationAPI
import co.shepherd.sports.domain.datasources.events.EventsLocalDataSource
import co.shepherd.sports.domain.datasources.events.EventsRemoteDataSource
import co.shepherd.sports.domain.datasources.schedule.ScheduleLocalDataSource
import co.shepherd.sports.domain.datasources.schedule.ScheduleRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {
    @Provides
    @Singleton
    fun provideEventsRemoteDataSource(applicationAPI: ApplicationAPI) =
        EventsRemoteDataSource(applicationAPI)

    @Provides
    @Singleton
    fun provideScheduleRemoteDataSource(applicationAPI: ApplicationAPI) =
        ScheduleRemoteDataSource(applicationAPI)

    @Provides
    @Singleton
    fun provideEventsLocalDataSource(eventsDao: EventsDao) =
        EventsLocalDataSource(eventsDao)

    @Provides
    @Singleton
    fun provideScheduleLocalDataSource(scheduleDao: ScheduleDao) =
        ScheduleLocalDataSource(scheduleDao)

}