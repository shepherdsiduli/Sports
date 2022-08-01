package co.shepherd.sports.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()
//
//    @Provides
//    fun provideGson(): Gson {
//        return GsonBuilder()
//            .registerTypeAdapter(
//                OffsetDateTime::class.java, OffsetDateTimeTypeAdapter()
//            ).registerTypeAdapter(
//                LocalTime::class.java, LocalTimeTypeAdapter()
//            ).create()
//    }
}