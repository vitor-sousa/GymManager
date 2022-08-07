package com.vitorsousa.gymmanager.presentation.treinos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vitorsousa.gymmanager.databinding.TreinoItemBinding
import com.vitorsousa.gymmanager.domain.models.Treino


interface TreinoItemListener {
    fun onItemSelected(position: Int)
}

interface DeleteTreinoItemListener {
    fun onDeleteClickListener(id: String, position: Int)
}


class TreinoAdapter(
    private val onItemSelectedListener: TreinoItemListener,
    private val onDeleteTreinoItemListener: DeleteTreinoItemListener
): RecyclerView.Adapter<TreinoAdapter.ViewHolder>() {

    private val mDiffer: AsyncListDiffer<Treino> = AsyncListDiffer(this, DIFF_CALLBACK)


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
        val item = mDiffer.currentList[position]
        holder.bindItem(item)
        holder.view.setOnClickListener {
            onItemSelectedListener.onItemSelected(position)
        }
        holder.deleteButton.setOnClickListener {
            onDeleteTreinoItemListener.onDeleteClickListener(
                id = item.treinoId,
                position = holder.layoutPosition
            )
        }
    }

    fun submitList(list: List<Treino?>?) {
        mDiffer.submitList(list)
    }


    override fun getItemCount(): Int = mDiffer.currentList.size

    inner class ViewHolder(private val binding: TreinoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val view: View = binding.root
        val deleteButton = binding.deleteButton

        fun bindItem(item: Treino) {
            binding.treino = item
            binding.executePendingBindings()
        }
    }


    object DIFF_CALLBACK: DiffUtil.ItemCallback<Treino>() {
        override fun areItemsTheSame(oldItem: Treino, newItem: Treino): Boolean {
            return newItem.treinoId == oldItem.treinoId
        }

        override fun areContentsTheSame(oldItem: Treino, newItem: Treino): Boolean {
            return oldItem == newItem
        }

    }

}