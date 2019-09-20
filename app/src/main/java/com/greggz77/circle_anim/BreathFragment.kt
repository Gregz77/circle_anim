package com.greggz77.circle_anim

import android.animation.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_breath.*


class BreathFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_breath, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val scale_x = PropertyValuesHolder.ofFloat(View.SCALE_X,0.1f, 3f)
        val scale_y = PropertyValuesHolder.ofFloat(View.SCALE_Y,0.1f, 3f)
        val scale_xs = PropertyValuesHolder.ofFloat(View.SCALE_X,3f, 0.1f)
        val scale_ys = PropertyValuesHolder.ofFloat(View.SCALE_Y,3f, 0.1f)

        val scaleTextUp = ObjectAnimator.ofPropertyValuesHolder(textView, scale_x, scale_y).apply {
            duration = 2500
        }
        val scaleTextDown = ObjectAnimator.ofPropertyValuesHolder(textView, scale_xs, scale_ys).apply {
            duration = 2500
        }

        val animatorSet = AnimatorSet()
        animatorSet.play(scaleTextUp)
            .before(scaleTextDown)
        animatorSet.start()


        animatorSet.addListener(object : AnimatorListenerAdapter() {

            private var mCanceled: Boolean = false

            override fun onAnimationStart(animation: Animator) {
                mCanceled = false
            }
            override fun onAnimationCancel(animation: Animator) {
                mCanceled = true
            }
            override fun onAnimationEnd(animation: Animator) {
                if (!mCanceled) {
                    animation.start()
                }
            }

        })
        animatorSet.start()
    }
}