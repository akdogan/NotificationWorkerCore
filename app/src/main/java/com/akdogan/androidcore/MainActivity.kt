package com.akdogan.androidcore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akdogan.androidcore.ui.main.MainFragment
import com.akdogan.androidcore.ui.main.createNotificationChannel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        createNotificationChannel(application)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}