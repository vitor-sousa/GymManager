package com.vitorsousa.gymmanager.presentation.treinos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vitorsousa.gymmanager.R
import com.vitorsousa.gymmanager.databinding.FragmentTreinoDetailBinding
import com.vitorsousa.gymmanager.presentation.MainActivity
import com.vitorsousa.gymmanager.presentation.exercicios.DeleteExercicioItemListener
import com.vitorsousa.gymmanager.presentation.exercicios.ExercicioAdapter
import com.vitorsousa.gymmanager.presentation.exercicios.ExercicioItemListener
import com.vitorsousa.gymmanager.presentation.exercicios.ExercicioViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TreinoDetailFragment : Fragment(), ExercicioItemListener, DeleteExercicioItemListener {

    private var _binding: FragmentTreinoDetailBinding? = null
    private val binding get() = _binding!!
    private val args: TreinoDetailFragmentArgs by navArgs()
    private val treinoViewModel: TreinoViewModel by activityViewModels()
    private val exercicioViewModel: ExercicioViewModel by viewModels()
    private lateinit var adapter: ExercicioAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTreinoDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = exercicioViewModel
        adapter = ExercicioAdapter(this, this)
        binding.exerciciosRecyclerView.apply {
            this.adapter = this@TreinoDetailFragment.adapter
            this.layoutManager = LinearLayoutManager(context)
        }
        setupObservers()
    }

    private fun setupObservers() {
        treinoViewModel.treinos.observe(viewLifecycleOwner) {
            val treino = it[args.position]
            binding.treino = treino
            (requireActivity() as MainActivity).supportActionBar?.title = treino.nome
        }

        exercicioViewModel.exercicios.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.editButton.setOnClickListener {
            findNavController().navigate(TreinoDetailFragmentDirections.actionTreinoDetailFragmentToNewTreinoFragment(args.position.toString()))
        }

        binding.addExercicioFloatButton.setOnClickListener {
            findNavController().navigate(TreinoDetailFragmentDirections.actionTreinoDetailFragmentToNewExercicioFragment(args.treinoId))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(position: Int) {
        TODO()
    }

    override fun onDeleteClickListener(id: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_exercise)
            .setMessage(R.string.are_you_sure_delete_exercise)
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .setPositiveButton(R.string.delete) { _, _ ->
                exercicioViewModel.deleteExercicio(id)
            }
            .create()
            .show()
    }
}