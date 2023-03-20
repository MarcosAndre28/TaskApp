package com.example.taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.R
import com.example.taskapp.data.model.Status
import com.example.taskapp.data.model.Task
import com.example.taskapp.databinding.FragmentTodoBinding
import com.example.taskapp.ui.adapter.TaskAdapter
import com.example.taskapp.ui.adapter.TaskTopAdapter


class TodoFragment : Fragment() {

    private var _binding : FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskTopAdapter: TaskTopAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()

        initRecyclerView()
        getTask()
    }

    private fun initListener(){
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_formTaskFragment)
        }
    }

    private fun initRecyclerView(){

        taskTopAdapter = TaskTopAdapter { task, option ->
            optionSelected(task,option)
        }

        taskAdapter = TaskAdapter(requireContext()){task, option ->
            optionSelected(task,option)
        }

        val concatAdapter = ConcatAdapter(taskTopAdapter,taskAdapter)

        with(binding.rvTasks){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = concatAdapter

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

        val teskTopList = listOf(
            Task("0","Top list",Status.TODO),
        )

     val teskList = listOf(
         Task("0","Criar nova tela do app",Status.TODO),
         Task("1","Validar informações na tela de login",Status.TODO),
         Task("2","Adicionar nova funcionalidade no app",Status.TODO),
         Task("3","Salvar token no localmente",Status.TODO),
         Task("4","Criar funcionalidade de logout no app",Status.TODO)
     )



        taskTopAdapter.submitList(teskTopList)

        taskAdapter.submitList(teskList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}