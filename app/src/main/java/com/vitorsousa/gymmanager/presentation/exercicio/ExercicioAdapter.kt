package com.vitorsousa.gymmanager.presentation.exercicio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vitorsousa.gymmanager.databinding.ExercicioItemBinding
import com.vitorsousa.gymmanager.databinding.TreinoItemBinding
import com.vitorsousa.gymmanager.domain.models.Exercicio
import com.vitorsousa.gymmanager.domain.models.Treino


interface ExercicioItemListener {
    fun onItemSelected(position: Int)
}

interface DeleteExercicioItemListener {
    fun onDeleteClickListener(id: String)
}


class ExercicioAdapter(
    private val onItemSelectedListener: ExercicioItemListener,
    private val onDeleteTreinoItemListener: DeleteExercicioItemListener
): RecyclerView.Adapter<ExercicioAdapter.ViewHolder>() {

    private val mDiffer: AsyncListDiffer<Exercicio> = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ExercicioItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDiffer.currentList[position]
        holder.bindItem(item)
        holder.view.setOnClickListener {
            onItemSelectedListener.onItemSelected(position)
        }
        holder.deleteButton.setOnClickListener {
            onDeleteTreinoItemListener.onDeleteClickListener(
                id = item.exercicioId
            )
        }
    }

    fun submitList(list: List<Exercicio?>?) {
        mDiffer.submitList(list)
    }


    override fun getItemCount(): Int = mDiffer.currentList.size

    inner class ViewHolder(private val binding: ExercicioItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val view: View = binding.root
        val deleteButton = binding.deleteButton

        fun bindItem(item: Exercicio) {
            binding.exercico = item
            binding.executePendingBindings()
        }
    }


    object DIFF_CALLBACK: DiffUtil.ItemCallback<Exercicio>() {
        override fun areItemsTheSame(oldItem: Exercicio, newItem: Exercicio): Boolean {
            return newItem.exercicioId == oldItem.exercicioId
        }

        override fun areContentsTheSame(oldItem: Exercicio, newItem: Exercicio): Boolean {
            return oldItem == newItem
        }

    }

}