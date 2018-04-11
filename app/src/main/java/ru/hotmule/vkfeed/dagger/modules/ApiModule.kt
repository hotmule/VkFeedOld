package ru.hotmule.vkfeed.dagger.modules

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.hotmule.vkfeed.app.VkApi
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class])
class ApiModule {
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) : VkApi = retrofit.create(VkApi::class.java)
}