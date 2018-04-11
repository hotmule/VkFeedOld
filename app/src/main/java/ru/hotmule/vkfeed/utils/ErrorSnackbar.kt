package ru.hotmule.vkfeed.utils

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import ru.hotmule.vkfeed.R

class ErrorSnackbar {
    companion object {
        fun show(context: Context, view: View, action: View.OnClickListener){
            Snackbar.make(view, context.getString(R.string.no_connection), Snackbar.LENGTH_INDEFINITE)
                    .setAction(context.getString(R.string.refresh), action)
                    .show()
        }
    }
}