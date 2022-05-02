package com.timusandrei.analogclock.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import com.timusandrei.analogclock.Clock
import com.timusandrei.analogclock.R

class ClockFragment : Fragment() {

    private var clock : Clock? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_clock, container, false)
        val surface : LinearLayout = view.findViewById<LinearLayout>(R.id.clock)
        clock = Clock(surface.context);
        surface.addView(clock)
        setHasOptionsMenu(true)
        return view

    }

    override fun onPause() {
        super.onPause()
        clock?.pause()

    }

    override fun onResume() {
        super.onResume()
        clock?.resume()
    }
}