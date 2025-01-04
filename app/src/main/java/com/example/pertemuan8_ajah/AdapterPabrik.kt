package com.example.pertemuan8_ajah

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pertemuan8_ajah.databinding.ItemPabrikBinding

class AdapterPabrik(private val data: List<ModelPabrik>) :
    RecyclerView.Adapter<AdapterPabrik.PabrikViewHolder>() {
    class PabrikViewHolder(private val binding: ItemPabrikBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(employee: ModelPabrik) {
            binding.tvNamaPabrik.text = employee.namapabrik
            binding.tvLokasiPabrik.text = employee.lokasipabrik
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PabrikViewHolder  {
        val binding = ItemPabrikBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PabrikViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PabrikViewHolder, position: Int) {
        val data = data[position]
        holder.bind(data)
    }
    override fun getItemCount(): Int = data.size
}