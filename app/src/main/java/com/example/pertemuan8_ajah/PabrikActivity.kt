package com.example.pertemuan8_ajah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pertemuan8_ajah.databinding.ActivityPabrikBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PabrikActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPabrikBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPabrikBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvPabrik.layoutManager = LinearLayoutManager(this)
        binding.rvPabrik.setHasFixedSize(true)
        showData()
        tambahData()
    }
    private fun showData() {
        val dataRef = FirebaseDatabase.getInstance().getReference("Pabrik")
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val pabrikList = mutableListOf<ModelPabrik>()
                val adapter = AdapterPabrik(pabrikList)
                for (dataSnapshot in snapshot.children) {
                    val pabrikKey = dataSnapshot.getValue(ModelPabrik::class.java)
                    pabrikKey?.let {
                        pabrikList.add(it)
                        Log.d("cek data firebase", it.toString())
                    }
                }
                if (pabrikList.isNotEmpty()) {
                    binding.rvPabrik.adapter = adapter
                } else {
                    Toast.makeText(this@PabrikActivity, "Data not found",
                        Toast.LENGTH_LONG).show()
                }
            }
            override fun onCancelled(snapshot: DatabaseError) {
// Handle onCancelled
            }
        })
    }
    private fun tambahData(){
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val pabrikRef: DatabaseReference = database.getReference("Pabrik")
        binding.fabAddPabrik.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_pabrik, null)
            MaterialAlertDialogBuilder(this)
                .setTitle("Tambah Pabrik")
                .setView(dialogView)
                .setPositiveButton("Tambah") { dialog, _ ->
                    val pabrikName =
                        dialogView.findViewById<EditText>(R.id.editTextPabrikName).text.toString()
                    val lokasiPabrik = dialogView.findViewById<EditText>(R.id.editTextLokasiPabrik).text.toString()
                    val pabrikData = HashMap<String, Any>()
                    pabrikData["namapabrik"] = pabrikName
                    pabrikData["lokasipabrik"] = lokasiPabrik
                    val newPabrikRef = pabrikRef.push()
                    newPabrikRef.setValue(pabrikData)
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}