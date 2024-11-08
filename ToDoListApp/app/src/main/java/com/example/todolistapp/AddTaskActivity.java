package com.example.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.todolistapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {
    private EditText editTaskName, editTaskDeadline, editTaskDuration, editTaskDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTaskName = findViewById(R.id.editTaskName);
        editTaskDeadline = findViewById(R.id.editTaskDeadline);
        editTaskDuration = findViewById(R.id.editTaskDuration);
        editTaskDescription = findViewById(R.id.editTaskDescription);

        Button btnSaveTask = findViewById(R.id.btnSaveTask);
        btnSaveTask.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        String name = editTaskName.getText().toString();
        String deadline = editTaskDeadline.getText().toString();
        int duration = Integer.parseInt(editTaskDuration.getText().toString());
        String description = editTaskDescription.getText().toString();

        Task task = new Task(name, deadline, duration, description);
        Intent intent = new Intent();
        intent.putExtra("task", task);
        setResult(RESULT_OK, intent);
        finish();
    }
}