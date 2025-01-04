package com.example.pertemuan8_ajah

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pertemuan8_ajah.databinding.ActivityRestoranBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RestoranActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestoranBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestoranBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvRestoran.layoutManager = LinearLayoutManager(this)
        binding.rvRestoran.setHasFixedSize(true)
        showData()
        tambahData()
    }
    private fun showData() {
        val dataRef1 = FirebaseDatabase.getInstance().getReference("Restoran")
        dataRef1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val restoranList = mutableListOf<ModelRestoran>()
                val adapter = AdapterRestoran(restoranList)
                for (dataSnapshot in snapshot.children) {
                    val restoranKey = dataSnapshot.getValue(ModelRestoran::class.java)
                    restoranKey?.let {
                        restoranList.add(it)
                        Log.d("cek data firebase", it.toString())
                    }
                }
                if (restoranList.isNotEmpty()) {
                    binding.rvRestoran.adapter = adapter
                } else {
                    Toast.makeText(this@RestoranActivity, "Data not found",
                        Toast.LENGTH_LONG).show()
                }
            }
            override fun onCancelled(snapshot: DatabaseError) {
// Handle onCancelled
            }
        })
    }
    private fun tambahData(){
        val database1: FirebaseDatabase = FirebaseDatabase.getInstance()
        val restoranRef: DatabaseReference = database1.getReference("Restoran")
        binding.fabAddRestoran.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.upload_dialog_restoran, null)
            MaterialAlertDialogBuilder(this)
                .setTitle("Tambah Restoran")
                .setView(dialogView)
                .setPositiveButton("Tambah") { dialog, _ ->
                    val restoranName =
                        dialogView.findViewById<EditText>(R.id.editTextNamaRestoran).text.toString()
                    val lokasi = dialogView.findViewById<EditText>(R.id.editTextLokasi).text.toString()
                    val restoranData = HashMap<String, Any>()
                    restoranData["namaRestoran"] = restoranName
                    restoranData["lokasi"] = lokasi
                    val newRestoranRef = restoranRef.push()
                    newRestoranRef.setValue(restoranData)
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}