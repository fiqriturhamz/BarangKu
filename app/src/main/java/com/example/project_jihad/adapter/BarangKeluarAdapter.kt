package com.example.project_jihad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_jihad.DataItemBarangKeluar
import com.example.project_jihad.R
import com.example.project_jihad.ResponseGetBarangKeluar

class BarangKeluarAdapter (private val list : ArrayList<DataItemBarangKeluar?>) : RecyclerView.Adapter<BarangKeluarAdapter.BarangKeluarViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BarangKeluarAdapter.BarangKeluarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_barang_keluar,parent,false)
        return BarangKeluarViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: BarangKeluarAdapter.BarangKeluarViewHolder,
        position: Int
    ) {
        val getData = list[position]
        holder.tvDeskripsiBarangKeluar.text = getData?.deskripsi
        holder.tvIdBarangKeluar.text = getData?.id
        holder.tvJenisBarangKeluar.text = getData?.jenisBarang
        holder.tvJumlahBarangKeluar.text = getData?.jumlahBarang
        holder.tvNamaBarangKeluar.text = getData?.namaBarang
        holder.tvTanggalBarangKeluar.text =getData?.tgl
    }

    override fun getItemCount(): Int = list.size
    inner class BarangKeluarViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val tvIdBarangKeluar = itemView.findViewById<TextView>(R.id.tv_input_id_barang_keluar_di_barang_keluar)
        val tvNamaBarangKeluar = itemView.findViewById<TextView>(R.id.tv_input_nama_barang_keluar_di_barang_keluar)
        val tvJenisBarangKeluar = itemView.findViewById<TextView>(R.id.tv_input_jenis_barang_keluar_di_barang_keluar)
        val tvJumlahBarangKeluar = itemView.findViewById<TextView>(R.id.tv_input_jumlah_barang_keluar_di_barang_keluar)
        val tvTanggalBarangKeluar = itemView.findViewById<TextView>(R.id.tv_input_tanggal_barang_keluar_di_barang_keluar)
        val tvDeskripsiBarangKeluar = itemView.findViewById<TextView>(R.id.tv_input_deskripsi_barang_keluar_di_barang_keluar)
    }
}