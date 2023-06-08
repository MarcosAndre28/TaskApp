package com.example.taskapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskapp.data.model.Task

class TaskViewModel : ViewModel() {

    private val _taskUpdate = MutableLiveData<Task>()
    val taskUpdate get() = _taskUpdate

    fun setUpdateTask(task: Task){
        _taskUpdate.value = task
    }
}