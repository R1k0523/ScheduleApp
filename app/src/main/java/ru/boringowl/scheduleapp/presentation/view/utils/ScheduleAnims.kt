package ru.boringowl.scheduleapp.presentation.view.utils

import android.content.Context
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import ru.boringowl.scheduleapp.R

class ScheduleAnims {
    companion object {
        fun rightIn(context: Context):LayoutAnimationController {
            return AnimationUtils.loadLayoutAnimation(context,
                    R.anim.fragment_animation_right)
        }
        fun rightOut(context: Context):LayoutAnimationController {
            return AnimationUtils.loadLayoutAnimation(context,
                    R.anim.fragment_animation_right_out)
        }
        fun leftIn(context: Context):LayoutAnimationController {
            return AnimationUtils.loadLayoutAnimation(context,
                    R.anim.fragment_animation_left)
        }
        fun leftOut(context: Context):LayoutAnimationController {
            return AnimationUtils.loadLayoutAnimation(context,
                    R.anim.fragment_animation_left_out)
        }
    }
}