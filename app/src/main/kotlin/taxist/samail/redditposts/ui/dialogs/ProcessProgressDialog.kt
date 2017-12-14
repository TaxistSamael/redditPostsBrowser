package taxist.samail.redditposts.ui.dialogs

import android.app.Activity
import android.app.ProgressDialog
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import taxist.samail.redditposts.R

class ProcessProgressDialog(val activity: Activity) : ProgressDialog(activity) {

    init {
        this.window?.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_process_progress)
    }

    fun show(isCancelable: Boolean = false) {
        if (this.isShowing || activity.isFinishing) return
        setCancelable(isCancelable)
        super.show()
    }

    override fun hide() {
        if (this.isShowing) cancel()
    }
}