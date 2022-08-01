package co.shepherd.sports.di

import co.shepherd.sports.domain.usecase.EventsUseCase
import co.shepherd.sports.domain.usecase.ScheduleUseCase
import co.shepherd.sports.repository.EventsRepository
import co.shepherd.sports.repository.ScheduleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Provides
    @Singleton
    fun provideEventsUseCase(eventsRepository: EventsRepository) =
        EventsUseCase(eventsRepository)

    @Provides
    @Singleton
    fun provideScheduleUseCase(scheduleRepository: ScheduleRepository) =
        ScheduleUseCase(scheduleRepository)

}