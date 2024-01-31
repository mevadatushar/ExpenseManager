package com.example.expensemanager

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.expensemanager.dashbord.Dasbhord_Activity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({ // This method will be executed once the timer is over
            val i = Intent(this@MainActivity, Dasbhord_Activity::class.java)
            startActivity(i)



            finish()
        }, 1500)
    }
}