package ru.hotmule.vkfeed.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = SkipStrategy::class)
interface AuthView : MvpView {
    fun login()
}