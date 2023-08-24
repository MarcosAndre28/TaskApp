package com.example.taskapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskapp.data.model.Task
import com.example.taskapp.util.StateView

class TaskViewModel : ViewModel() {


    private val _taskList = MutableLiveData<StateView<List<Task>>>()
    val taskList: LiveData<StateView<List<Task>>> = _taskList

    private val _taskDelete = MutableLiveData<StateView<Task>>()
    val taskDelete: LiveData<StateView<Task>> = _taskDelete

    private val _taskInsert = MutableLiveData<StateView<Task>>()
    val taskInsert get() = _taskInsert

    private val _taskUpdate = MutableLiveData<StateView<Task>>()
    val taskUpdate get() = _taskUpdate

    fun getTask() {
    }

    fun insertTask(task: Task) {
    }

    fun updateTask(task: Task) {
    }

    fun deleteTask(task: Task) {
    }
}