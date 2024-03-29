package com.books.ex.apitest.common.logger

import android.R
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.Fragment

class LogFragment : Fragment() {
    lateinit var logView: LogView
    lateinit var mScrollView: ScrollView

    private fun inflateViews(): View {
        mScrollView = ScrollView(activity)
        val scrollParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        mScrollView.layoutParams = scrollParams
        logView = LogView(activity)

        val logParams = ViewGroup.LayoutParams(scrollParams)
        logParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        logView.layoutParams = logParams
        logView.isClickable = true
        logView.isFocusable = true
        logView.typeface = Typeface.MONOSPACE

        val paddingDips = 16
        val scale = resources.displayMetrics.density.toDouble()
        val paddingPixels = (paddingDips * scale + .5).toInt()
        logView.setPadding(paddingPixels, paddingPixels, paddingPixels, paddingPixels)
        logView.compoundDrawablePadding = paddingPixels
        logView.gravity = Gravity.BOTTOM
        logView.setTextAppearance(activity, R.style.TextAppearance_Holo_Small)
        mScrollView.addView(logView)

        return mScrollView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val result = inflateViews()
        logView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN)
            }
        })
        return result
    }
}