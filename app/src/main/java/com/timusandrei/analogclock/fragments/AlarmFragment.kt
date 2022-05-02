package com.timusandrei.analogclock.fragments

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.timusandrei.analogclock.AlarmView
import com.timusandrei.analogclock.Clock
import com.timusandrei.analogclock.R
import java.lang.Exception
import java.util.*

class AlarmFragment : Fragment() {

    private var alarmView : AlarmView? = null

    override fun onPause() {
        super.onPause()
        alarmView?.pause()

    }

    override fun onResume() {
        super.onResume()
        alarmView?.resume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_alarm, container, false)
        val surface : LinearLayout = view.findViewById<LinearLayout>(R.id.alarm)

        val calendar : Calendar = Calendar.getInstance()

        val alarmMessage = view.findViewById<EditText>(R.id.alarm_message)
        val setTime = view.findViewById<Button>(R.id.set_time)
        val setAlarm = view.findViewById<Button>(R.id.set_alarm)

        alarmView = AlarmView(surface.context);
        surface.addView(alarmView)
        setHasOptionsMenu(true)

        setTime.setOnClickListener {
            var currentHour = Calendar.HOUR_OF_DAY
            var currentMin = Calendar.MINUTE

            val timePickerDialog = TimePickerDialog(activity, { timePicker: TimePicker, hourOfDay: Int, minutes: Int ->
                alarmView!!.hour = hourOfDay
                alarmView!!.min = minutes
            }, currentHour, currentMin, false)

            timePickerDialog.show()
        }

        setAlarm.setOnClickListener {
            try {
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                val mess : String = alarmMessage.text.toString()
                intent.putExtra(AlarmClock.EXTRA_HOUR, alarmView!!.hour)
                intent.putExtra(AlarmClock.EXTRA_MINUTES, alarmView!!.min)
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, mess)
                if ( intent.resolveActivity(activity?.packageManager!!) != null) {
                    startActivity(intent);
                    Toast.makeText(context, "Alarm setted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,
                        "There is no app that support this action",
                        Toast.LENGTH_SHORT).show()
                }
            } catch (ex : Exception) {
                Toast.makeText(context, ex.message, Toast.LENGTH_SHORT).show()
                ex.message?.let { it1 -> Log.e("E", it1) }
            }
        }

        return view
    }
}