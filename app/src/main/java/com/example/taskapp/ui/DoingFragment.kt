package com.example.taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.R
import com.example.taskapp.data.model.Status
import com.example.taskapp.data.model.Task
import com.example.taskapp.databinding.FragmentDoingBinding
import com.example.taskapp.databinding.FragmentSplashBinding
import com.example.taskapp.ui.adapter.TaskAdapter

class DoingFragment : Fragment() {

    private var _binding : FragmentDoingBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        getTask()
    }

    private fun initRecyclerView(){
        taskAdapter = TaskAdapter(requireContext()){task, option ->
            optionSelected(task,option)
        }


        with(binding.rvTasks){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter

        }
    }

    private fun optionSelected(task: Task, option: Int){
        when(option){
            TaskAdapter.SELECT_REMOVE -> {
                Toast.makeText(requireContext(), "Removendo ${task.description}", Toast.LENGTH_SHORT).show()
            }

            TaskAdapter.SELECT_EDIT -> {
                Toast.makeText(requireContext(), "Editando ${task.description}", Toast.LENGTH_SHORT).show()
            }

            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(requireContext(), "Detalhes ${task.description}", Toast.LENGTH_SHORT).show()
            }

            TaskAdapter.SELECT_NEXT -> {
                Toast.makeText(requireContext(), "Next ${task.description}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getTask() {
        val teskList = listOf(
            Task("0","Criar nova tela do app",Status.TODO),
            Task("1","Validar informações na tela de login",Status.TODO),
            Task("2","Adicionar nova funcionalidade no app",Status.TODO),
            Task("3","Salvar token no localmente",Status.TODO),
            Task("4","Criar funcionalidade de logout no app",Status.TODO)
        )

        taskAdapter.submitList(teskList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}