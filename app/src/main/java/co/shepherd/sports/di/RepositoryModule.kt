package co.shepherd.sports.di

import co.shepherd.sports.domain.datasources.events.EventsLocalDataSource
import co.shepherd.sports.domain.datasources.events.EventsRemoteDataSource
import co.shepherd.sports.domain.datasources.schedule.ScheduleLocalDataSource
import co.shepherd.sports.domain.datasources.schedule.ScheduleRemoteDataSource
import co.shepherd.sports.repository.EventsRepository
import co.shepherd.sports.repository.ScheduleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideEventsRepository(
        eventsRemoteDataSource: EventsRemoteDataSource,
        eventsLocalDataSource: EventsLocalDataSource,
    ) = EventsRepository(eventsRemoteDataSource, eventsLocalDataSource)

    @Provides
    @Singleton
    fun provideScheduleRepository(
        scheduleRemoteDataSource: ScheduleRemoteDataSource,
        scheduleLocalDataSource: ScheduleLocalDataSource,
    ) = ScheduleRepository(scheduleRemoteDataSource, scheduleLocalDataSource)

}