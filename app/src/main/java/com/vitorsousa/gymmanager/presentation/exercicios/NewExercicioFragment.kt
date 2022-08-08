package com.vitorsousa.gymmanager.presentation.exercicios

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.vitorsousa.gymmanager.R
import com.vitorsousa.gymmanager.databinding.FragmentNewExercicioBinding
import com.vitorsousa.gymmanager.domain.models.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewExercicioFragment : DialogFragment() {

    private var _binding: FragmentNewExercicioBinding? = null
    private val binding get() = _binding!!
    private val exercicioViewModel: ExercicioViewModel by viewModels()
    private var uri: Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            binding.image.setImageURI(uri)
            this.uri = uri
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewExercicioBinding.inflate(inflater, container, false)
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
                textInputLayout.error = getString(R.string.cant_be_empty)
                true
            } else -> {
                textInputLayout.isErrorEnabled = true
                textInputLayout.error = null
                false
            }
        }
    }

    private fun setupObservers() {
        binding.nomeTextField.editText?.setText(exercicioViewModel.exercicio.nome)
        binding.observacoesTextField.editText?.setText(exercicioViewModel.exercicio.observacoes)

        binding.nomeTextField.editText?.addTextChangedListener { nome ->
            nome?.let {
                exercicioViewModel.exercicio.nome = nome.toString()
            }
        }

        binding.observacoesTextField.editText?.addTextChangedListener { observacoes ->
            observacoes?.let {
                exercicioViewModel.exercicio.observacoes = observacoes.toString()
            }
        }

        binding.createButton.setOnClickListener {
            if(!isEditTextsEmpty(binding.nomeTextField) && !isEditTextsEmpty(binding.observacoesTextField))
                exercicioViewModel.saveExercicio(uri)
        }

        binding.imageButton.setOnClickListener {
            selectImageToUpload()
        }

        binding.image.setOnClickListener {
            selectImageToUpload()
        }

        exercicioViewModel.saveStatus.observe(viewLifecycleOwner) {
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

    private fun selectImageToUpload() {
        getContent.launch("image/*")
    }




}