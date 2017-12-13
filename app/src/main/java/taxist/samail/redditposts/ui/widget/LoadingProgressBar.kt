package taxist.samail.redditposts.ui.widget

import android.content.Context
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.ProgressBar
import taxist.samail.redditposts.R

class LoadingProgressBar : ProgressBar {

    constructor(context: Context?) : super(context) {
        changeProgressColor()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        changeProgressColor()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        changeProgressColor()
    }

    private fun changeProgressColor() {
        val color = ContextCompat.getColor(context, R.color.color_progress_bar)
        indeterminateDrawable.mutate()
        indeterminateDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }
}