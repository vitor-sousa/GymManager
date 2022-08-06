package com.vitorsousa.gymmanager.presentation.treinos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitorsousa.gymmanager.databinding.TreinoItemBinding
import com.vitorsousa.gymmanager.domain.models.Treino

interface TreinoItemListener {
    fun onItemSelected(id: String)
}

interface DeleteTreinoItemListener {
    fun onDeleteClickListener(id: String, position: Int)
}


class TreinoAdapter(
    private val onItemSelectedListener: TreinoItemListener,
    private val onDeleteTreinoItemListener: DeleteTreinoItemListener
): RecyclerView.Adapter<TreinoAdapter.ViewHolder>() {

    private var treinosLista = mutableListOf<Treino>()

    fun updateList(treinos: List<Treino>) {
        treinosLista.clear()
        treinosLista.addAll(treinos)
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
        val item = treinosLista[position]
        holder.bindItem(item)
        holder.view.setOnClickListener {
            onItemSelectedListener.onItemSelected(item.treinoId)
        }
        holder.deleteButton.setOnClickListener {
            onDeleteTreinoItemListener.onDeleteClickListener(
                id = item.treinoId,
                position = holder.layoutPosition
            )
        }
    }

    fun removeItemAt(position: Int) {
        treinosLista.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = treinosLista.size

    inner class ViewHolder(private val binding: TreinoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val view: View = binding.root
        val deleteButton = binding.deleteButton

        fun bindItem(item: Treino) {
            binding.treino = item
            binding.executePendingBindings()
        }
    }
}