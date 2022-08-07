package com.vitorsousa.gymmanager.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import com.vitorsousa.gymmanager.databinding.FragmentTreinoDetailBinding
import com.vitorsousa.gymmanager.presentation.MainActivity
import com.vitorsousa.gymmanager.presentation.exercicio.DeleteExercicioItemListener
import com.vitorsousa.gymmanager.presentation.exercicio.ExercicioAdapter
import com.vitorsousa.gymmanager.presentation.exercicio.ExercicioItemListener
import com.vitorsousa.gymmanager.presentation.treinos.TreinoAdapter
import com.vitorsousa.gymmanager.presentation.treinos.TreinoViewModel


class TreinoDetailFragment : Fragment(), ExercicioItemListener, DeleteExercicioItemListener {


    private var _binding: FragmentTreinoDetailBinding? = null
    private val binding get() = _binding!!
    val args: TreinoDetailFragmentArgs by navArgs()
    private val treinoViewModel: TreinoViewModel by activityViewModels()
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
            adapter.submitList(treino.exercicios)
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
        println(position)
    }

    override fun onDeleteClickListener(id: String, position: Int) {
        println(id)
    }
}