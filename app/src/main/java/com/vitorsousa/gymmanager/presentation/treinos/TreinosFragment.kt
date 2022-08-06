package com.vitorsousa.gymmanager.presentation.treinos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vitorsousa.gymmanager.R
import com.vitorsousa.gymmanager.databinding.FragmentTreinosBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TreinosFragment: Fragment(), TreinoItemListener, DeleteTreinoItemListener {

    private var _binding: FragmentTreinosBinding? = null
    private val binding get() = _binding!!
    private val treinoViewModel: TreinoViewModel by activityViewModels()
    private lateinit var adapter: TreinoAdapter

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
        findNavController().navigate(R.id.action_treinosFragment_to_treinoDetailFragment)
    }

    override fun onDeleteClickListener(id: String, position: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Excluir Treino")
            .setMessage("Tem certeza que deseja excluir esse treino?")
            .setNegativeButton("Cancelar") { _, _ -> }
            .setPositiveButton("Excluir") { _, _ ->
                treinoViewModel.deleteTreino(id)
            }
            .create()
            .show()
    }

    private fun setupObservers() {
        binding.addButton.setOnClickListener {
            showNewTreinoDialog()
        }
        treinoViewModel.treinos.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        adapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.treinosRecyclerView.scrollToPosition(positionStart)
            }
        })

    }

    private fun showNewTreinoDialog() {
        findNavController().navigate(R.id.action_treinosFragment_to_newTreinoFragment)
    }


}