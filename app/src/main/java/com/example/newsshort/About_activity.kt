package com.example.newsshort

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_about.*

class About_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        back_btn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        //facebook
        val i=Intent()
        i.action=Intent.ACTION_VIEW
        facebook.setOnClickListener {
            i.data= Uri.parse("https://www.facebook.com/our.sujoy/")
            startActivity(i)
        }
        instagram.setOnClickListener {
            i.data= Uri.parse("https://www.instagram.com/0_sujoy/")
            startActivity(i)
        }
        github.setOnClickListener {
            i.data=Uri.parse("https://github.com/Sujoy-Mistry")
            startActivity(i)
        }


    }
}