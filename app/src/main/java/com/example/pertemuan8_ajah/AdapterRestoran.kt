package com.example.pertemuan8_ajah

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pertemuan8_ajah.databinding.ItemRestoranBinding

class AdapterRestoran (private val data1: List<ModelRestoran>):
    RecyclerView.Adapter<AdapterRestoran.RestoranViewHolder>() {
    class RestoranViewHolder(private val binding: ItemRestoranBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(restoran: ModelRestoran) {
            binding.tvNamaRestoran.text = restoran.namaRestoran
            binding.tvLokasi.text = restoran.lokasi
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestoranViewHolder {
        val binding = ItemRestoranBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RestoranViewHolder(binding)
    }
    override fun onBindViewHolder(holder: RestoranViewHolder, position: Int) {
        val data = data1[position]
        holder.bind(data)
    }
    override fun getItemCount(): Int = data1.size
}