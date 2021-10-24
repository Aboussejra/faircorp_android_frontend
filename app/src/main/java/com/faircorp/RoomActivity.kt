package com.faircorp

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.faircorp.model.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val param = intent.getStringExtra(ROOM_NAME_PARAM)
        val roomName = findViewById<TextView>(R.id.txt_room_name)
        roomName.text = param

        val id = intent.getLongExtra(ROOM_NAME_PARAM, 0)

        //get request from API
        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices().roomsApiService.findById(id).execute(); }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {
                        var room = it.body();
                        if (room != null) {

                            findViewById<TextView>(R.id.txt_room_name).text = room.name
                            findViewById<TextView>(R.id.room_curent_temp).text =
                                room.currentTemperature.toString()
                            findViewById<TextView>(R.id.room_target_temp).text =
                                room.targetTemperature.toString()

                            Toast.makeText(
                                this@RoomActivity,
                                "Target Temperature " + room?.targetTemperature,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Error on room loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

        }


    }

}