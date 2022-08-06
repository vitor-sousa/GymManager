package com.vitorsousa.gymmanager.presentation.treinos

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.vitorsousa.gymmanager.R
import com.vitorsousa.gymmanager.databinding.FragmentNewTreinoBinding
import com.vitorsousa.gymmanager.domain.models.DataState


class NewTreinoFragment : DialogFragment() {

    private var _binding: FragmentNewTreinoBinding? = null
    private val binding get() = _binding!!
    private val treinoViewModel: TreinoViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewTreinoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return dialog
    }

    private fun isEditTextsEmpty(textInputLayout: TextInputLayout): Boolean {
        return when (textInputLayout.editText?.text.isNullOrEmpty()) {
            true -> {
                textInputLayout.isErrorEnabled = true
                textInputLayout.error = "Está vazio"
                true
            } else -> {
                textInputLayout.isErrorEnabled = true
                textInputLayout.error = null
                false
            }
        }
    }

    private fun setupObservers() {
        binding.nomeTextField.editText?.setText(treinoViewModel.nome)
        binding.descricaoTextField.editText?.setText(treinoViewModel.descricao)

        binding.nomeTextField.editText?.addTextChangedListener { nome ->
            nome?.let {
                treinoViewModel.nome = nome.toString()
            }
        }

        binding.descricaoTextField.editText?.addTextChangedListener { descricao ->
            descricao?.let {
                treinoViewModel.descricao = descricao.toString()
            }
        }

        binding.createButton.setOnClickListener {
            if(!isEditTextsEmpty(binding.nomeTextField) && !isEditTextsEmpty(binding.descricaoTextField))
                treinoViewModel.saveTreino()
        }

        treinoViewModel.saveStatus.observe(viewLifecycleOwner) {
            binding.progressCircular.visibility = View.GONE
            when (it) {
                DataState.LOADING -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                DataState.SUCCESS -> {
                    Toast.makeText(requireContext(), "Created with success", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                else -> {
                    Toast.makeText(requireContext(), "ERROR: ${it.name}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}