package com.greggz77.circle_anim

import android.animation.*
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.View.ALPHA
import android.view.ViewGroup
import android.widget.Toast.*
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

        //Expand and shrink animation
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X,1f, 2f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y,1f, 2f)
        val scaleXS = PropertyValuesHolder.ofFloat(View.SCALE_X,2f, 1f)
        val scaleYS = PropertyValuesHolder.ofFloat(View.SCALE_Y,2f, 1f)
        val scaleTextUp = ObjectAnimator.ofPropertyValuesHolder(textView, scaleX, scaleY)
        val scaleTextDown = ObjectAnimator.ofPropertyValuesHolder(textView, scaleXS, scaleYS)
        //Alpha animation for directions
        val alphaTextIn = PropertyValuesHolder.ofFloat(ALPHA, 1f, 0f)
        val alphaTextOut = PropertyValuesHolder.ofFloat(ALPHA, 0f, 1f)
        val alphaIn = ObjectAnimator.ofPropertyValuesHolder(breatheInText, alphaTextIn)
        val alphaOut = ObjectAnimator.ofPropertyValuesHolder(breatheOutText, alphaTextOut)

        val animatorSet = AnimatorSet()
        //Repeating animation indefinitely
        animatorSet.addListener(object : AnimatorListenerAdapter() {

            private var mCanceled: Boolean = false

            override fun onAnimationStart(animation: Animator) {
                mCanceled = false
                //Timer countdown to stop anim. after 5 min. - set timer duration to 5' - current duration just for testing
                object : CountDownTimer(20000, 1000) {

                    override fun onTick(millisUntilFinished: Long) {
                    }
                    override fun onFinish() {
                        breatheInText.text = ""
                        breatheOutText.text = ""
                        makeText(activity?.applicationContext, "Excersize completed", LENGTH_LONG).show()
                        mCanceled = true
                    }
                }.start()
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
        animatorSet.play(scaleTextUp)
            .with(alphaIn)
            .before(scaleTextDown)
                .with(alphaOut)
        animatorSet.duration = 3500
        animatorSet.start()
    }
}