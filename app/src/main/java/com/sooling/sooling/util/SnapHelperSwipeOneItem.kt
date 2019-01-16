package com.sooling.sooling.util

import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView


class SnapHelperSwipeOneItem : LinearSnapHelper() {
    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager?, velocityX: Int, velocityY: Int): Int {

        // 한 번에 한 개의 아이템만 swipe 되도록 설정
        if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider)
            return RecyclerView.NO_POSITION

        val currentView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        val currentPosition = layoutManager.getPosition(currentView)

        return if (currentPosition == RecyclerView.NO_POSITION) {
            RecyclerView.NO_POSITION
        } else currentPosition
    }
}