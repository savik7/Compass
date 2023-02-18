package com.savik.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.getSystemService

class MainActivity : AppCompatActivity(), SensorEventListener {
    var manager: SensorManager? = null
    var current_degree:Int = 0
    var tvDegree: TextView? = null
    var imDinamic: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        tvDegree = findViewById(R.id.tvDegree)
        imDinamic = findViewById(R.id.imDinamic)
    }

    override fun onResume() {
        super.onResume()
        manager?.registerListener(this, manager?.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME) // для старых
        //manager?.registerListener(this, manager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME) // для новых
    }

    override fun onPause() {
        super.onPause()
        manager?.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val degree: Int = p0?.values?.get(0)?.toInt()!!
        //Log.d("myLog", degree.toString())
        tvDegree?.setText(degree.toString())
        val rotationAnim = RotateAnimation(current_degree.toFloat(), (-degree).toFloat(), Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f)
        rotationAnim.duration = 210
        rotationAnim.fillAfter = true
        current_degree = -degree
        imDinamic?.startAnimation(rotationAnim)

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}