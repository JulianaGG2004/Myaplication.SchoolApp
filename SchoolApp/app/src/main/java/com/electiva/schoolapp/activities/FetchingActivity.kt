package com.electiva.schoolapp.activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.electiva.schoolapp.R
import com.electiva.schoolapp.adapters.CalAdapter
import com.electiva.schoolapp.model.CalificacionesModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.content.Intent

class FetchingActivity : AppCompatActivity() {

    private lateinit var calRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var calList: ArrayList<CalificacionesModel>
    private lateinit var dbRef : DatabaseReference
//se crea la vista para traer la lista de datos registrados en firebase
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_calificaciones)

        calRecyclerView = findViewById(R.id.rvCali)
        calRecyclerView.layoutManager = LinearLayoutManager(this)
        calRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        calList = arrayListOf<CalificacionesModel>()

        getCalificaionesData()
    }
//obtener la informacion a actulizar
    private fun getCalificaionesData(){
        calRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Calificaciones")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                calList.clear()
                if(snapshot.exists()){
                    for (calSnap in snapshot.children){
                        val calData = calSnap.getValue(CalificacionesModel::class.java)
                        calList.add(calData!!)
                    }
                    val mAdapter = CalAdapter(calList)
                    calRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : CalAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent( this@FetchingActivity, CalificacionDetailActivity::class.java)

                            //pull extras
                            intent.putExtra("id", calList[position].id)
                            intent.putExtra("nomEstu", calList[position].nomEstu)
                            intent.putExtra("curso", calList[position].curso)
                            intent.putExtra("materia", calList[position].materia)
                            intent.putExtra("valorCali", calList[position].valorCali)
                            intent.putExtra("nomActi", calList[position].nomActi)
                            startActivity(intent)
                        }

                    })

                    calRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}