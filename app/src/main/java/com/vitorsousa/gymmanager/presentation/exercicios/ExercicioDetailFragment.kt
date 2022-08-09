package com.vitorsousa.gymmanager.presentation.exercicios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.onNavDestinationSelected
import com.vitorsousa.gymmanager.databinding.FragmentExercicioDetailBinding
import com.vitorsousa.gymmanager.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExercicioDetailFragment : Fragment(){

    private var _binding: FragmentExercicioDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ExercicioDetailFragmentArgs by navArgs()
    private val exercicioViewModel: ExercicioViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExercicioDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        setupObservers()
    }

    private fun setupObservers() {
        exercicioViewModel.exercicios.observe(viewLifecycleOwner) { exerciciosLista ->
            val exercicio = exerciciosLista.find {
                it.exercicioId == args.exercicioId
            }
            binding.exercicio = exercicio
            (requireActivity() as MainActivity).supportActionBar?.title = exercicio?.nome
        }
        binding.editButton.setOnClickListener {
            findNavController().navigate(ExercicioDetailFragmentDirections.actionExercicioDetailFragmentToNewExercicioFragment(args.treinoId, args.exercicioId))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}