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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate.now


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp(viewModel: TaskViewModel) {
    val tasks by viewModel.allTasks.observeAsState(emptyList())
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    var descriptionState by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Simple MVVM Todo") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    TextField(
                        value = textState,
                        onValueChange = { textState = it },
                        label = { Text("New Task") },
                    )
                    TextField(
                        value = descriptionState,
                        onValueChange = { descriptionState = it },
                        label = { Text("Task Description") },
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                    if (textState.text.isNotEmpty() && descriptionState.text.isNotEmpty()) {
                        viewModel.addTask(textState.text, descriptionState.text)
                        textState = TextFieldValue("")
                        descriptionState = TextFieldValue("")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(task: Task, onDelete: () -> Unit) {
    var categoryColor by remember { mutableStateOf(Color(0xFFFFFFFF)) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = categoryColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            Arrangement.SpaceBetween
        )
        {
            Text("Task: " + task.title)
            Text("Description:")
            Text(task.description + '\n')

            // Set up for Dropdown Menu
            val categories = listOf("Select a Category","Work", "School", "Personal", "Important")

            var selectedCategory by remember { mutableStateOf(categories.first()) }
            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()        // required for correct dropdown placement
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            })
                    }
                }
            }

            when (selectedCategory){
                "Work" -> categoryColor = Color(0xFF1656AD)
                "School" -> categoryColor = Color(0xFF008000)
                "Personal" -> categoryColor = Color(0xFF800080)
                "Important" -> categoryColor = Color(0xFFFF0000)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Date Added: " + now().toString())
                TextButton(onClick = onDelete) { Text("Delete") }
            }

        }
    }
}
