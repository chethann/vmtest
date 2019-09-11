package com.phonepe.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.framelayout,
            MainFragment()
        ).commit()

        button.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RVTestActivity::class.java)
            startActivity(intent)
        })
    }
}
