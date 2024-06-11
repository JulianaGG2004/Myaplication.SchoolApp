package com.electiva.schoolapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.electiva.schoolapp.R
import com.electiva.schoolapp.model.CalificacionesModel

class CalAdapter (private val calList: ArrayList<CalificacionesModel>) :
    RecyclerView.Adapter<CalAdapter.ViewHolder>(){

        private lateinit var mListener: onItemClickListener

        interface onItemClickListener{
            fun onItemClick(position: Int)
        }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

//Listar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_lista_calificaciones, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentCal = calList[position]
        holder.tvNomEstu.text = currentCal.nomEstu
        holder.tvCurso.text = currentCal.curso
        holder.tvMateria.text = currentCal.materia
        holder.tvValorCali.text = currentCal.valorCali
        holder.tvNomActi.text = currentCal.nomActi
    }

    override fun getItemCount(): Int {
        return calList.size
    }

    class ViewHolder(itemView : View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val tvNomEstu: TextView = itemView.findViewById(R.id.tvNomEstu)
        val tvCurso: TextView = itemView.findViewById(R.id.tvCurso)
        val tvMateria: TextView = itemView.findViewById(R.id.tvMateria)
        val tvValorCali: TextView = itemView.findViewById(R.id.tvValorCali)
        val tvNomActi: TextView = itemView.findViewById(R.id.tvNomActi)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }

    }





}