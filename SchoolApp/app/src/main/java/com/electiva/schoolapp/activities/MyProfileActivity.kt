package com.electiva.schoolapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.electiva.schoolapp.R

class MyProfileActivity : AppCompatActivity() {
    private lateinit var btnCalificaciones: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_myprofile)

        btnCalificaciones=findViewById(R.id.btnCalificaciones)


        btnCalificaciones.setOnClickListener{
            val intent = Intent(this, GestionarCaliActivity::class.java)
            startActivity(intent)
        }

    }
}