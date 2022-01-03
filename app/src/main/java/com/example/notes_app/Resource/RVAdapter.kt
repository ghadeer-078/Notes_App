package com.example.notes_app.Resource

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_app.MainActivity
import com.example.notes_app.Model.Note
import com.example.notes_app.databinding.ItemRowBinding


class RVAdapter(var notes: List<Note>, val activity: MainActivity) :
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
            val personData = " ${note.content}"

            tvPerson.text = personData
            btnEdit.setOnClickListener {

                activity.raiseDialog(note)
            }

            btnDelete.setOnClickListener {

                val builder = AlertDialog.Builder(holder.itemView.context)
                builder.setTitle("Are you sure wou want to delete this note?")
                builder.setPositiveButton("Delete") { _, _ -> activity.delete(note) }
                builder.setNegativeButton("Cancel") { _, _ -> }

                builder.show()
            }
        }
    }


    override fun getItemCount() = notes.size

}