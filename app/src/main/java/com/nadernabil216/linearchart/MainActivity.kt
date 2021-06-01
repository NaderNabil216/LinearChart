package com.nadernabil216.linearchart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val charts: LinearChart = findViewById(R.id.chart)
        charts.addChartUnits {
            chartUnits = arrayListOf(
                LinearChartUnit("Vacations", "#d99a28", 4),
                LinearChartUnit("Permissions", "#49a487", 4),
                LinearChartUnit("KPIs", "#955023", 4)
            )
            labelTextColorHexaString = "#868894"
            chartUnitsTotalRatio = 9
            labelFont = R.font.stc_regular
            labelTextSize = 15
        }
    }
}