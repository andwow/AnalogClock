package com.timusandrei.analogclock

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.timusandrei.analogclock.fragments.AlarmFragment
import com.timusandrei.analogclock.fragments.ClockFragment
import com.timusandrei.analogclock.fragments.TimerFragment
import com.timusandrei.analogclock.singletons.ColorSingleton


class MainActivity : AppCompatActivity() {

    val colors = ColorSingleton.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(getColor("backGround") != Color.TRANSPARENT) {
            colors.backGround = getColor("backGround")
            colors.indicators = getColor("indicators")
            colors.hourHand = getColor("hourHand")
            colors.minHand = getColor("minHand")
            colors.secHand = getColor("secHand")
            colors.milisecHand = getColor("milisecHand")
        } else {
            setColor("backGround",colors.backGround)
            setColor("indicators", colors.indicators)
            setColor("hourHand", colors.hourHand)
            setColor("minHand", colors.minHand)
            setColor("secHand", colors.secHand)
            setColor("milisecHand", colors.milisecHand)
        }
        changeFragment()
    }

    override fun onResume() {
        super.onResume()
        setColor("backGround",colors.backGround)
        setColor("indicators", colors.indicators)
        setColor("hourHand", colors.hourHand)
        setColor("minHand", colors.minHand)
        setColor("secHand", colors.secHand)
        setColor("milisecHand", colors.milisecHand)
    }

    private fun changeFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.actual_fragment, ClockFragment())
        fragmentTransaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id : Int  = item.itemId

        when(id) {
            R.id.clock_item -> {
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.actual_fragment, ClockFragment())
                transaction.commit()
            }
            R.id.timer_item -> {
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.actual_fragment, TimerFragment())
                transaction.commit()
            }
            R.id.alarm_item -> {
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.actual_fragment, AlarmFragment())
                transaction.commit()
            }
            R.id.customization_item -> {
                val intent = Intent(this, CustomizationActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    public fun createAlarm(hour : Int, minutes : Int) {
//        try {
//            val intent = Intent(AlarmClock.ACTION_SET_ALARM)
//            intent.putExtra(AlarmClock.EXTRA_HOUR, hour)
//            intent.putExtra(AlarmClock.EXTRA_MINUTES, minutes)
//            intent.putExtra(AlarmClock.EXTRA_MESSAGE, "AAA")
//            if ( intent.resolveActivity(packageManager) != null) {
//                startActivity(intent);
//                Toast.makeText(this, "Alarm setted", Toast.LENGTH_SHORT)
//            } else {
//                Toast.makeText(this,
//                    "There is no app that support this action",
//                    Toast.LENGTH_SHORT)
//            }
//        } catch (ex : Exception) {
//            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT)
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun getColor(key : String): Int {
        val sharedPref = getPreferences(MODE_PRIVATE)
        return sharedPref.getInt(key, Color.TRANSPARENT)
    }

    fun setColor(key : String, color: Int) {
        val sharedPref = getPreferences(MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(key, color)
        editor.apply()
    }
}