package com.example.notes_app.Resource

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_app.DB.DatabaseHelper
import com.example.notes_app.MainActivity
import com.example.notes_app.Model.Person
import com.example.notes_app.databinding.ItemRowBinding
import com.google.android.material.snackbar.Snackbar


class RVAdapter(var notes: List<Person>, val activity: MainActivity) :
    RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note = notes[position]

        holder.binding.apply {
            //${person.pk} -
            val personData = " ${note.title} \n  ${note.content}"

            tvPerson.text = personData
            btnEdit.setOnClickListener {

                activity.raiseDialog(note.pk)
            }

            btnDelete.setOnClickListener {

                val builder = AlertDialog.Builder(holder.itemView.context)
                builder.setTitle("Are you sure wou want to delete this note?")
                builder.setPositiveButton("Delete") { _, _ -> activity.delete(note.pk) }
                builder.setNegativeButton("Cancel") { _, _ -> }

                builder.show()
            }
        }
    }


    override fun getItemCount() = notes.size

}