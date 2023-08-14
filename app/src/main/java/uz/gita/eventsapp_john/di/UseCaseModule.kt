package uz.gita.eventsapp_john.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.eventsapp_john.domain.usecase.EventsUseCase
import uz.gita.eventsapp_john.domain.usecase.impl.EventsUseCaseImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface UseCaseModule {

    @[Binds Singleton]
    fun bindEventUseCase(impl: EventsUseCaseImpl): EventsUseCase
}