package com.vitorsousa.gymmanager.presentation.treinos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitorsousa.gymmanager.databinding.TreinoItemBinding
import com.vitorsousa.gymmanager.domain.models.Treino

interface TreinoItemListener {
    fun onItemSelected(id: Int)
}

class TreinoAdapter(
    private val listener: TreinoItemListener
): RecyclerView.Adapter<TreinoAdapter.ViewHolder>() {

    private var values: List<Treino> = ArrayList()

    fun updateList(movies: List<Treino>) {
        values = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            TreinoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bindItem(item)
//        holder.view.setOnClickListener {
//            item.id?.let { id -> listener.onItemSelected(id) }
//        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: TreinoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val view: View = binding.root

        fun bindItem(item: Treino) {
            binding.treino = item
            binding.executePendingBindings()
        }
    }
}