package br.com.aramizu.themoviedb.presentation.ui.custom.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.TextView

import br.com.aramizu.themoviedb.R

/**
 * Custom Dialog to show any kind of information, but with a custom layout.
 */
class GenericDialogOkCancel(internal var context: Context, title: String?, body: String?, negative: String?, positive: String?) : Dialog(context) {

    internal var dialogTitle: TextView? = null
    internal var dialogBody: TextView? = null
    internal var btnPositive: TextView? = null
    internal var btnNegative: TextView? = null

    internal var response: Boolean = false

    internal var mListener: GenericDialogOkCancelListener? = null

    init {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.generic_dialog_ok_cancel)
        configureLabel(dialogTitle, title)
        configureLabel(dialogBody, body)
        configureLabel(btnNegative, negative)
        configureLabel(btnPositive, positive)

        this.setCancelable(false)
        this.setCanceledOnTouchOutside(false)
    }

    private fun configureLabel(view: TextView?, text: String?) {
        if (text != null) {
            view!!.text = text
        } else {
            view!!.visibility = View.GONE
        }
    }

    fun onNegativeButtonClick() {
        returnResponse(isPositive = false)
    }

    fun onPositiveButtonClick() {
        returnResponse(isPositive = true)
    }

    /**
     * Return if the positive button option has been chosen
     * @param isPositive
     */
    fun returnResponse(isPositive: Boolean) {
        if (mListener != null) {
            mListener!!.response(isPositive)
        }
        dismiss()
    }

    fun setListenerResponse(listener: GenericDialogOkCancelListener) {
        mListener = listener
    }

    fun showDialog() {
        if (!(context as Activity).isFinishing) {
            show()
        }
    }
}
