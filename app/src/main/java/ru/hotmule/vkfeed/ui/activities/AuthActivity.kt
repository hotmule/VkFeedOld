package ru.hotmule.vkfeed.ui.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import kotlinx.android.synthetic.main.activity_auth.*
import ru.hotmule.vkfeed.R
import ru.hotmule.vkfeed.mvp.presenters.AuthPresenter
import ru.hotmule.vkfeed.mvp.views.AuthView
import com.vk.sdk.api.VKError
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import ru.hotmule.vkfeed.utils.TokenPreference
import android.graphics.Color.parseColor
import android.view.WindowManager
import android.os.Build


class AuthActivity : MvpAppCompatActivity(), AuthView {

    @InjectPresenter
    lateinit var authPresenter: AuthPresenter

    companion object {
        fun buildIntent(context: Context) = Intent(context, AuthActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        changeStatusBarColor()

        authButton.setOnClickListener {
            authPresenter.onAuthButtonClicked()
        }
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 23) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = applicationContext.getColor(R.color.colorPrimaryDark)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {

                    override fun onResult(res: VKAccessToken) {
                        val preference = TokenPreference(applicationContext)
                        preference.setAccessToken(res.accessToken)

                        startActivity(FeedActivity.buildIntent(applicationContext))
                        finish()
                    }

                    override fun onError(error: VKError) {
                        Toast.makeText(
                                applicationContext,
                                getString(R.string.error_auth_message),
                                Toast.LENGTH_LONG).show()
                    }
                }))
            super.onActivityResult(requestCode, resultCode, data)
    }

    override fun login() {
        VKSdk.login(this, VKScope.WALL, VKScope.FRIENDS)
    }
}