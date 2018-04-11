package ru.hotmule.vkfeed.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.hotmule.vkfeed.mvp.views.AuthView

@InjectViewState
class AuthPresenter : MvpPresenter<AuthView>() {

    fun onAuthButtonClicked() {
        viewState.login()
    }
}