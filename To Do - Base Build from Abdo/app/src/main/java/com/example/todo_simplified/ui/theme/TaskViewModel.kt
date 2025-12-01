package com.example.todo_simplified.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todo_simplified.data.Task
import com.example.todo_simplified.data.TaskDatabase
import com.example.todo_simplified.data.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>

    init {
        val dao = TaskDatabase.getInstance(application).taskDao()
        repository = TaskRepository(dao)
        allTasks = repository.allTasks
    }

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            repository.addTask(Task(title = title, description = description))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}
