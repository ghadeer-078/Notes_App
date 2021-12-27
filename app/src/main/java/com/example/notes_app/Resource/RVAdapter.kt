package com.example.notes_app.Resource

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_app.Model.Person
import com.example.notes_app.databinding.ItemRowBinding


class RVAdapter : RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {

    private var people = emptyList<Person>()

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
        val person = people[position]

        holder.binding.apply {
            val personData = "${person.pk} - ${person.name} - ${person.location}"
            tvPerson.text = personData
        }
    }


    override fun getItemCount() = people.size


    fun update(people: ArrayList<Person>) {
        this.people = people
        notifyDataSetChanged()
    }
}