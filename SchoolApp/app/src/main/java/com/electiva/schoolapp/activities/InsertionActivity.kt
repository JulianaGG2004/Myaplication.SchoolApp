package com.electiva.schoolapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.electiva.schoolapp.R
import com.electiva.schoolapp.model.CalificacionesModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {
// Declaracion de campos a registrar
    private lateinit var editTextNomEstu: EditText
    private lateinit var editTextCurso: EditText
    private lateinit var editTextMateria: EditText
    private lateinit var editTextValorCali: EditText
    private lateinit var editTextNomActi: EditText
    private lateinit var btnSave: Button

    private lateinit var dbRef : DatabaseReference
//Se inserta los datos digitados en la vista calificaciones
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calificaciones)

        editTextNomEstu = findViewById(R.id.editTextNomEstu)
        editTextCurso = findViewById(R.id.editTextCurso)
        editTextMateria = findViewById(R.id.editTextMateria)
        editTextValorCali = findViewById(R.id.editTextValorCali)
        editTextNomActi = findViewById(R.id.editTextNomActi)
        btnSave = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Calificaciones")

        btnSave.setOnClickListener {
            saveCalificacionesData()
        }
    }
//Se guardan los datos y se confirma su registro exitoso
    private fun saveCalificacionesData(){
        //getting values

        val nomEstu = editTextNomEstu.text.toString()
        val curso = editTextCurso.text.toString()
        val materia = editTextMateria.text.toString()
        val valorCali = editTextValorCali.text.toString()
        val nomActi = editTextNomActi.text.toString()

        if(nomEstu.isEmpty()){
            editTextNomEstu.error = "Porfavor ingresar el nombre del Estudiante"
        }
        if(curso.isEmpty()){
            editTextCurso.error = "Porfavor ingresar el curso"
        }
        if(materia.isEmpty()){
            editTextMateria.error = "Porfavor ingresar la materia"
        }
        if(valorCali.isEmpty()){
            editTextValorCali.error = "Porfavor ingresar la calificación"
        }
        if(nomActi.isEmpty()){
            editTextNomActi.error = "Porfavor ingresar nombre actividad"
        }

        val id = dbRef.push().key!!

        val calificaciones = CalificacionesModel(id, nomEstu, curso, materia, valorCali, nomActi)

        dbRef.child(id).setValue(calificaciones)
            .addOnCompleteListener{
                Toast.makeText(this, "Datos insertados exitosamente!", Toast.LENGTH_LONG).show()

                editTextNomEstu.text.clear();
                editTextCurso.text.clear();
                editTextMateria.text.clear();
                editTextValorCali.text.clear();
                editTextNomActi.text.clear();

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}
