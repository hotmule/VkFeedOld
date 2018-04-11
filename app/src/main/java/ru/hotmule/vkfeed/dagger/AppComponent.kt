package ru.hotmule.vkfeed.dagger

import dagger.Component
import ru.hotmule.vkfeed.dagger.modules.VkModule
import ru.hotmule.vkfeed.mvp.presenters.FeedPresenter
import ru.hotmule.vkfeed.mvp.presenters.PostPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [VkModule::class])
interface AppComponent {

    fun inject(feedPresenter: FeedPresenter)
    fun inject(postPresenter: PostPresenter)
}
