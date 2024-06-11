package com.electiva.schoolapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.electiva.schoolapp.R
import com.electiva.schoolapp.model.CalificacionesModel
import com.google.firebase.database.FirebaseDatabase

class CalificacionDetailActivity: AppCompatActivity() {

    private lateinit var tvId: TextView
    private lateinit var tvNomEstu: TextView
    private lateinit var tvCurso: TextView
    private lateinit var tvMateria: TextView
    private lateinit var tvValorCali: TextView
    private lateinit var tvNomActi: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calificaciones_detail)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("id").toString(),
                intent.getStringExtra("nomEstu").toString(),
            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("id").toString()
            )

        }
    }

    private fun deleteRecord(id:String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Calificaciones").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener{
            Toast.makeText(this, "Calificacion eliminada", Toast.LENGTH_LONG).show()
            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)

        }.addOnFailureListener { error ->
            Toast.makeText(this, "Error al eliminar ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView(){
        tvId = findViewById(R.id.tvId)
        tvNomEstu = findViewById(R.id.tvNomEstu)
        tvCurso = findViewById(R.id.tvCurso)
        tvMateria = findViewById(R.id.tvMateria)
        tvValorCali = findViewById(R.id.tvValorCali)
        tvNomActi = findViewById(R.id.tvNomActi)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

    }
//Se trae la informacion a consultar
    private fun setValuesToViews(){
        tvId.text = intent.getStringExtra("id")
        tvNomEstu.text = intent.getStringExtra("nomEstu")
        tvCurso.text = intent.getStringExtra("curso")
        tvMateria.text = intent.getStringExtra("materia")
        tvValorCali.text = intent.getStringExtra("valorCali")
        tvNomActi.text = intent.getStringExtra("nomActi")
    }
//Se crea una ventana alternativa con la infromacion para poderla actualziar
    private fun openUpdateDialog( id: String, nomEstu: String){
    val mDialog = AlertDialog.Builder(this)
    val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_dialog, null)

        mDialog.setView(mDialogView)

        val editTextNomEstu = mDialogView.findViewById<EditText>(R.id.editTextNomEstu)
        val editTextCurso = mDialogView.findViewById<EditText>(R.id.editTextCurso)
        val editTextMateria = mDialogView.findViewById<EditText>(R.id.editTextMateria)
        val editTextValorCali = mDialogView.findViewById<EditText>(R.id.editTextValorCali)
        val editTextNomActi = mDialogView.findViewById<EditText>(R.id.editTextNomActi)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        editTextNomEstu.setText(intent.getStringExtra("nomEstu").toString())
        editTextCurso.setText(intent.getStringExtra("curso").toString())
        editTextMateria.setText(intent.getStringExtra("materia").toString())
        editTextValorCali.setText(intent.getStringExtra("valorCali").toString())
        editTextNomActi.setText(intent.getStringExtra("nomActi").toString())

        mDialog.setTitle("Actualizando Información de $nomEstu")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
        updateCalData(
            id,
            editTextNomEstu.text.toString(),
            editTextCurso.text.toString(),
            editTextMateria.text.toString(),
            editTextValorCali.text.toString(),
            editTextNomActi.text.toString()
        )
            Toast.makeText(applicationContext, "Calificacion actualizada", Toast.LENGTH_LONG).show()

// we are setting update data our testviws
            tvNomEstu.text = editTextNomEstu.text.toString()
            tvCurso.text = editTextCurso.text.toString()
            tvMateria.text = editTextMateria.text.toString()
            tvValorCali.text = editTextValorCali.text.toString()
            tvNomActi.text = editTextNomActi.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateCalData(
        id: String,
        nomEstu: String,
        curso: String,
        materia: String,
        valorCali: String,
        nomActi: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Calificaciones").child(id)
        val calInfo = CalificacionesModel(id , nomEstu,curso, materia, valorCali, nomActi)
        dbRef.setValue(calInfo)
    }
}