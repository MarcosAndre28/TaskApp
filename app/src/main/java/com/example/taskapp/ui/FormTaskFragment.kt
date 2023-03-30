package com.example.taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskapp.R
import com.example.taskapp.data.model.Status
import com.example.taskapp.data.model.Task
import com.example.taskapp.databinding.FragmentFormTaskBinding
import com.example.taskapp.util.initToolbar
import com.example.taskapp.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task
    private var status: Status = Status.TODO
    private var newTask: Boolean = true

    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private val args: FormTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        reference = Firebase.database.reference
        auth = Firebase.auth

        initListener()
        getArgs()
    }


    private fun getArgs(){
        args.task.let {
            if (it != null){
                this.task = it
                configTask()
            }
        }
    }

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            validateData()
        }

        binding.rbStatus.setOnCheckedChangeListener { _, id ->
            status = when(id){
                R.id.rb_todo -> Status.TODO
                R.id.rbDoing -> Status.DOING
                else  -> Status.TODO
            }
        }

    }

    private fun configTask(){
        newTask = false
        status = task.status
        binding.textToolbar.setText(R.string.text_toolbar_update)

        binding.editDescription.setText(task.description)

        setStatus()
    }

    private fun setStatus(){
        binding.rbStatus.check(
            when(task.status){
                Status.TODO -> R.id.rb_todo
                Status.DOING -> R.id.rbDoing
                else -> R.id.rbDone
            }
        )
    }

    private fun validateData() {
        val description = binding.editDescription.text.toString().trim()

        if (description.isNotEmpty()) {
            binding.progressBar.isVisible = true
            if (newTask){
                task = Task()
                task.id =reference.database.reference.push().key ?: ""
            }
            task.description = description
            task.status = status
           saveTask()
        } else {
          showBottomSheet(message = getString(R.string.description_empty_form_fragment))
        }
    }

    private fun saveTask(){
        reference.child("tasks")
            .child(auth.currentUser?.uid ?: "")
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if (result.isSuccessful){
                    Toast.makeText(requireContext(), R.string.text_save_sucess_form_fragment, Toast.LENGTH_SHORT).show()
                    if (newTask){ // Nova tarefa
                        findNavController().popBackStack()
                    }
                    else{ // Editando tarefa
                        binding.progressBar.isVisible = false
                    }
                }
                else {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = getString(R.string.error_generic))
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}