package com.timusandrei.analogclock.fragments

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.set
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.timusandrei.analogclock.R
import com.timusandrei.analogclock.Timer
import java.util.*

class TimerFragment : Fragment() {

    private var timer : Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_timer, container, false)
        val surface : LinearLayout = view.findViewById<LinearLayout>(R.id.timer)
        val startStopButton = view.findViewById<Button>(R.id.start_stop_timer)
        val resetButton = view.findViewById<Button>(R.id.reset_timer)
        val limitHour = view.findViewById<EditText>(R.id.limit_hour)
        val limitMin = view.findViewById<EditText>(R.id.limit_minute)
        val limitSec = view.findViewById<EditText>(R.id.limit_second)

        timer = Timer(surface.context);
        surface.addView(timer)
        setHasOptionsMenu(true)

        limitHour.addTextChangedListener {
            if(limitHour != null && limitHour.text.isNotEmpty() && limitHour.text.isNotBlank()) {
                var hour: Int = limitHour.text.toString().toInt()
                timer!!.limitHour = hour
            } else {
                timer!!.limitHour = 0
            }
        }

        limitMin.addTextChangedListener {
            if(limitMin != null && limitMin.text.isNotEmpty() && limitMin.text.isNotBlank()) {
                var min: Int = limitMin.text.toString().toInt()
                timer!!.limitMin = min
            } else {
                timer!!.limitMin = 0
            }
        }

        limitSec.addTextChangedListener {
            if(limitSec != null && limitSec.text.isNotEmpty() && limitSec.text.isNotBlank()) {
                var sec: Int = limitSec.text.toString().toInt()
                timer!!.limitSec = sec
            } else {
                timer!!.limitSec = 0
            }
        }


        startStopButton.setOnClickListener {
            timer!!.turnOnOffTimer()
        }

        resetButton.setOnClickListener {
            timer!!.resetTimer()
        }

        return view

    }

    override fun onPause() {
        super.onPause()
        timer?.pause()

    }

    override fun onResume() {
        super.onResume()
        timer?.resume()
    }

}