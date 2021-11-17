package ru.boringowl.scheduleapp.presentation.view.utils

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import kotlin.math.abs


class OnSwipeTouchListener(context: Context?, onSwipeLeft: () -> Unit, onSwipeRight: () -> Unit) :
        OnTouchListener {
        private val gestureDetector: GestureDetector
        val onSwipeRight = onSwipeRight
        var onSwipeLeft  = onSwipeLeft
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            return gestureDetector.onTouchEvent(event)
        }



        private inner class GestureListener : SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                val distanceX = e2.x - e1.x
                val distanceY = e2.y - e1.y
                if (abs(distanceX) > abs(distanceY) && abs(distanceX) > Companion.SWIPE_DISTANCE_THRESHOLD && abs(
                        velocityX
                    ) > Companion.SWIPE_VELOCITY_THRESHOLD
                ) {
                    if (distanceX > 0) onSwipeRight() else onSwipeLeft()
                    return true
                }
                return false
            }


        }

    companion object {
        private const val SWIPE_DISTANCE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

        init {
            gestureDetector = GestureDetector(context, GestureListener())
        }
    }
