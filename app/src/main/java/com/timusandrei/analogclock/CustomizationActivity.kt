package com.timusandrei.analogclock

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.timusandrei.analogclock.R.id
import com.timusandrei.analogclock.singletons.ColorSingleton


class CustomizationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private enum class UsedColors {
        BLACK,
        WHITE,
        RED,
        MAGENTA,
        BLUE,
        GREEN,
        YELLOW
    }

    private val colorSingleton = ColorSingleton.getInstance()

    private val colors = arrayOf<String>("Black", "White", "Red", "Magenta", "Blue", "Green", "Yellow")

    private var adapter : ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customization)

        adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, colors)

        val backgroud = findViewById<Spinner>(id.background_color)
        val indicators = findViewById<Spinner>(id.indicators_color)
        val hours = findViewById<Spinner>(id.hours_hand_color)
        val minutes = findViewById<Spinner>(id.minutes_hand_color)
        val seconds = findViewById<Spinner>(id.seconds_hand_color)
        val milliseconds = findViewById<Spinner>(id.milliseconds_hand_color)

        backgroud.adapter = adapter;
        indicators.adapter = adapter;
        hours.adapter = adapter;
        minutes.adapter = adapter;
        seconds.adapter = adapter;
        milliseconds.adapter = adapter;

        backgroud.onItemSelectedListener = this
        indicators.onItemSelectedListener = this
        hours.onItemSelectedListener = this
        minutes.onItemSelectedListener = this
        seconds.onItemSelectedListener = this
        milliseconds.onItemSelectedListener = this

        setPosition(backgroud, colorSingleton.backGround)
        setPosition(indicators, colorSingleton.indicators)
        setPosition(hours, colorSingleton.hourHand)
        setPosition(minutes, colorSingleton.minHand)
        setPosition(seconds, colorSingleton.secHand)
        setPosition(milliseconds, colorSingleton.milisecHand)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val componentId: Int = parent?.id!!
        val usedColor: UsedColors = UsedColors.values().first { it.ordinal == position }
        when (usedColor) {
            UsedColors.BLACK -> {
                changeColor(componentId, Color.BLACK)
            }
            UsedColors.WHITE -> {
                changeColor(componentId, Color.WHITE)
            }
            UsedColors.RED -> {
                changeColor(componentId, Color.RED)
            }
            UsedColors.MAGENTA -> {
                changeColor(componentId, Color.MAGENTA)
            }
            UsedColors.BLUE -> {
                changeColor(componentId, Color.BLUE)
            }
            UsedColors.GREEN -> {
                changeColor(componentId, Color.GREEN)
            }
            UsedColors.YELLOW -> {
                changeColor(componentId, Color.YELLOW)
            }
        }
    }

    private fun setPosition(spinner: Spinner, color : Int) {
        when(color) {
            Color.BLACK -> {
                spinner.setSelection(adapter?.getPosition("Black")!!)
            }
            Color.WHITE -> {
                spinner.setSelection(adapter?.getPosition("White")!!)
            }
            Color.RED -> {
                spinner.setSelection(UsedColors.RED.ordinal)
            }
            Color.MAGENTA -> {
                spinner.setSelection(UsedColors.MAGENTA.ordinal)
            }
            Color.BLUE -> {
                spinner.setSelection(UsedColors.BLUE.ordinal)
            }
            Color.GREEN -> {
                spinner.setSelection(UsedColors.GREEN.ordinal)
            }
            Color.YELLOW -> {
                spinner.setSelection(UsedColors.YELLOW.ordinal)
            }
        }
    }

    private fun changeColor(id : Int, color : Int) {
        when(id) {
            R.id.background_color -> {
                ColorSingleton.getInstance().backGround = color
            }
            R.id.indicators_color -> {
                ColorSingleton.getInstance().indicators = color
            }
            R.id.hours_hand_color -> {
                ColorSingleton.getInstance().hourHand = color
            }
            R.id.minutes_hand_color -> {
                ColorSingleton.getInstance().minHand = color
            }
            R.id.seconds_hand_color -> {
                ColorSingleton.getInstance().secHand = color
            }
            R.id.milliseconds_hand_color -> {
                ColorSingleton.getInstance().milisecHand = color
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

}