package taxist.samail.redditposts.ui.feed

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class VerticalPaddingItemDecorator(val padding: Int, val firstItemPadding: Int = padding,
                                   val paddingFirstItem: Boolean = true,
                                   val paddingLastItem: Int = 0) : RecyclerView.ItemDecoration(){

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val itemPosition = parent.getChildAdapterPosition(view)

        if (itemPosition == 0) {
            val topPadding = if (paddingFirstItem) firstItemPadding else 0
            outRect.set(0, topPadding, 0, padding)
        }

        if (itemPosition != 0) {
            outRect.set(0, 0, 0, padding)
        }

        if (parent.adapter.itemCount == 1 || paddingLastItem == 0) return

        if (itemPosition == parent.adapter.itemCount - 1) {
            outRect.set(0, 0, 0, paddingLastItem)
        }
    }
}