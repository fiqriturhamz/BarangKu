package com.example.project_jihad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_jihad.R
import com.example.project_jihad.model.DataItemJenisBarang


class JenisBarangAdapter(private val listJenisBarang : ArrayList<DataItemJenisBarang?>)  : RecyclerView.Adapter<JenisBarangAdapter.JenisBarangViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JenisBarangAdapter.JenisBarangViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_jenis_barang,parent,false)
        return JenisBarangViewHolder(view)
    }

    override fun onBindViewHolder(holder: JenisBarangAdapter.JenisBarangViewHolder, position: Int) {
        val getDataJenisBarang = listJenisBarang[position]
        holder.tvInputIdJenisBarang.text = getDataJenisBarang?.id
        holder.tvInputJenisBarang.text = getDataJenisBarang?.jenisBarang
        holder.tvInputDeskripsiJenisBarang.text = getDataJenisBarang?.deskripsi
    }

    override fun getItemCount(): Int = listJenisBarang.size

    inner class JenisBarangViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvInputIdJenisBarang = itemView.findViewById<TextView>(R.id.tv_input_id_jenis_barang)
        val tvInputJenisBarang = itemView.findViewById<TextView>(R.id.tv_input_jenis_barang)
        val tvInputDeskripsiJenisBarang = itemView.findViewById<TextView>(R.id.tv_input_deskripsi_jenis_barang)
    }
}