package com.vitorsousa.gymmanager.presentation.treinos

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.behavior.SwipeDismissBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vitorsousa.gymmanager.databinding.FragmentTreinosBinding
import com.vitorsousa.gymmanager.domain.models.DataState
import com.vitorsousa.gymmanager.domain.models.Exercicio
import com.vitorsousa.gymmanager.domain.models.Treino
import com.vitorsousa.gymmanager.domain.repositories.TreinoRepository
import com.vitorsousa.gymmanager.presentation.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TreinosFragment: Fragment(), TreinoItemListener, DeleteTreinoItemListener {

    private var _binding: FragmentTreinosBinding? = null
    private val binding get() = _binding!!
    private val treinoViewModel: TreinoViewModel by activityViewModels()
    private lateinit var adapter: TreinoAdapter
    private lateinit var newFragment : NewTreinoFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTreinosBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = treinoViewModel
        adapter = TreinoAdapter(this, this)
        binding.treinosRecyclerView.apply {
            this.adapter = this@TreinosFragment.adapter
            this.layoutManager = LinearLayoutManager(context)
        }
        setupObservers()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(id: String) {
        println(id)
    }

    override fun onDeleteClickListener(id: String, position: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Excluir Treino")
            .setMessage("Tem certeza que deseja excluir esse treino?")
            .setNegativeButton("Cancelar") { _, _ -> }
            .setPositiveButton("Excluir") { _, _ ->
                treinoViewModel.deleteTreino(id)
                adapter.removeItemAt(position)
            }
            .create()
            .show()
    }

    private fun setupObservers() {
        binding.addButton.setOnClickListener {
            showNewTreinoDialog()
        }
        treinoViewModel.treinos.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
        treinoViewModel.saveStatus.observe(viewLifecycleOwner) {
            if (it == DataState.SUCCESS) {
                childFragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .remove(newFragment)
                    .commit()
            }
        }
    }

    private fun showNewTreinoDialog() {
        val fragmentManager = childFragmentManager
        newFragment = NewTreinoFragment()

        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction
            .add(newFragment, "newTreino")
            .addToBackStack(null)
            .commit()

    }


}