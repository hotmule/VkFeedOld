package ru.hotmule.vkfeed.dagger.modules

import dagger.Module
import dagger.Provides
import ru.hotmule.vkfeed.app.VkApi
import ru.hotmule.vkfeed.mvp.global.VkService
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class VkModule {
    @Provides
    @Singleton
    fun provideVkService(vkApi: VkApi) : VkService = VkService(vkApi)
}