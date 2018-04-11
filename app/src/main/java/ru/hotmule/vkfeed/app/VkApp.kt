package ru.hotmule.vkfeed.app

import android.app.Application
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKSdk
import ru.hotmule.vkfeed.dagger.AppComponent
import ru.hotmule.vkfeed.dagger.DaggerAppComponent
import ru.hotmule.vkfeed.ui.activities.AuthActivity

class VkApp : Application() {

    companion object {
        lateinit var appComonent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(this)

        appComonent = DaggerAppComponent
                .builder()
                .build()
    }

    var vkAccessTokenTracker: VKAccessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null)
                startActivity(AuthActivity.buildIntent(applicationContext))
        }
    }
}