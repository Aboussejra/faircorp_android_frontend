package com.faircorp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.faircorp.model.WindowService

class WindowActivity : BasicActivity() {

    val windowService = WindowService();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getLongExtra(WINDOW_NAME_PARAM, 1)
        val window = windowService.findById(id)

        if (window != null) {
            findViewById<TextView>(R.id.txt_window_name).text = window.name
            findViewById<TextView>(R.id.txt_window_room).text = window.room.name
            findViewById<TextView>(R.id.txt_room_current_temp).text = window.room.currentTemperature?.toString()
            findViewById<TextView>(R.id.txt_room_target_temp).text = window.room.targetTemperature?.toString()
            findViewById<TextView>(R.id.txt_window_status).text = window.status.toString()
        }
    }
}