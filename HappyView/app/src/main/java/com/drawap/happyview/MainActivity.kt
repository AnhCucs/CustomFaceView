package com.drawap.happyview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.drawap.library.HappyView
import com.drawap.library.StateFace

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAction()
        Log.d("stateFace", findViewById<HappyView>(R.id.faceHappy).happinessState.toString())
    }

    private fun initAction() {
        findViewById<HappyView>(R.id.happyButton).setOnClickListener {
            findViewById<HappyView>(R.id.faceHappy).happinessState = StateFace.HAPPY
            Log.d("stateFace", findViewById<HappyView>(R.id.faceHappy).happinessState.toString())
        }

        findViewById<HappyView>(R.id.sadButton).setOnClickListener {
            findViewById<HappyView>(R.id.faceHappy).happinessState = StateFace.SAD
            Log.d("stateFace", findViewById<HappyView>(R.id.faceHappy).happinessState.toString())
        }
    }
}