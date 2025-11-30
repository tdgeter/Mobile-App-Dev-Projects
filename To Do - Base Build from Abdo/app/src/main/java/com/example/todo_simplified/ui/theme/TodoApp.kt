package com.example.todo_simplified.ui.theme


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.todo_simplified.data.Task
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate.now


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp(viewModel: TaskViewModel) {
    val tasks by viewModel.allTasks.observeAsState(emptyList())
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Simple MVVM Todo") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = textState,
                    onValueChange = { textState = it },
                    label = { Text("New Task") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if (textState.text.isNotEmpty()) {
                        viewModel.addTask(textState.text)
                        textState = TextFieldValue("")
                    }
                }) {
                    Text("Add")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(tasks) { task ->
                    TaskItem(task = task, onDelete = { viewModel.deleteTask(task) })
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            Arrangement.SpaceBetween
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Task:" + task.title)
                Text("Date Added: " + now().toString())
                TextButton(onClick = onDelete) { Text("Delete") }
            }

            Text("Description")
        }
    }
}
