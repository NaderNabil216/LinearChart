package com.nadernabil216.linearchart

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.FontRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginTop
import com.google.android.material.card.MaterialCardView


class LinearChart : LinearLayoutCompat {

    private lateinit var llColors: LinearLayout
    private lateinit var llTexts: LinearLayout

    constructor(context: Context) : super(ContextThemeWrapper(context, R.style.MaterialAppTheme)) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) :
            super(ContextThemeWrapper(context, R.style.MaterialAppTheme), attrs) {
        init()
    }

    private fun init() {
        orientation = VERTICAL
        val cardView = LayoutInflater.from(context)
            .inflate(R.layout.colors_layout, this, false) as MaterialCardView
        llColors = cardView.findViewById(R.id.llcolors)
        llColors.clipToOutline = true

        llTexts = LinearLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.HORIZONTAL


        }

        addView(cardView)
        addView(llTexts)
        invalidate()
    }

    fun addChartUnits(configuration: LinearChartConfiguration.() -> Unit) {
        val config = LinearChartConfiguration().apply { configuration() }
        config.run {
            llColors.weightSum = config.chartUnitsTotalRatio.toFloat()
            llTexts.weightSum = config.chartUnitsTotalRatio.toFloat()

            addColorsSection(chartUnits)
            addTextsSection(this)
        }
    }

    private fun addColorsSection(chartUnits: ArrayList<LinearChartUnit>) {
        chartUnits.forEach {
            llColors.addView(getColoredSection(it))
        }
    }

    private fun getColoredSection(unit: LinearChartUnit): FrameLayout {
        val view = FrameLayout(context)
        view.apply {
            val params = LinearLayout.LayoutParams(
                0,
                50, unit.unitRatio.toFloat()
            )
            layoutParams = params
            setBackgroundColor(Color.parseColor(unit.chartColorHexaString))
        }
        return view
    }

    private fun addTextsSection(
        configuration: LinearChartConfiguration
    ) {
        configuration.chartUnits.forEach {
            llTexts.addView(getColoredText(it, configuration))
        }
    }

    private fun getColoredText(unit: LinearChartUnit, configuration: LinearChartConfiguration): TextView {
        val textView = TextView(context)
        textView.apply {
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT, unit.unitRatio.toFloat()
            )
            (layoutParams as MarginLayoutParams).setMargins(0,8,0,0)
            gravity = Gravity.START

            setTextColor(Color.parseColor(configuration.labelTextColorHexaString))
            text = unit.labelText
            textSize = configuration.labelTextSize.toFloat()
            configuration.labelFont?.let {
                setTypeface(ResourcesCompat.getFont(context, it),Typeface.NORMAL)
            }
        }
        return textView
    }


}

data class LinearChartUnit(
    var labelText: String = "",
    var chartColorHexaString: String = "#000000",
    var unitRatio: Int = 0
)

data class LinearChartConfiguration(
    var chartUnits: ArrayList<LinearChartUnit> = arrayListOf(),
    var chartUnitsTotalRatio: Int = 0,
    var labelTextColorHexaString: String = "#000000",
    var labelTextSize:Int =0,
    @FontRes var labelFont:Int? = 0
)