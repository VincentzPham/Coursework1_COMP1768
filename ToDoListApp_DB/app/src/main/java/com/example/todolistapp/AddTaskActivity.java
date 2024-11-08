package com.example.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import com.example.todolistapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {
    private EditText editTaskName, editTaskDeadline, editTaskDuration, editTaskDescription;
    private Spinner spinnerTaskStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTaskName = findViewById(R.id.editTaskName);
        editTaskDeadline = findViewById(R.id.editTaskDeadline);
        editTaskDuration = findViewById(R.id.editTaskDuration);
        editTaskDescription = findViewById(R.id.editTaskDescription);

        spinnerTaskStatus = findViewById(R.id.spinnerTaskStatus);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.task_status_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskStatus.setAdapter(adapter);

        Button btnSaveTask = findViewById(R.id.btnSaveTask);
        btnSaveTask.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        String name = editTaskName.getText().toString();
        String deadline = editTaskDeadline.getText().toString();
        int duration = Integer.parseInt(editTaskDuration.getText().toString());
        String description = editTaskDescription.getText().toString();
        String status = spinnerTaskStatus.getSelectedItem().toString();

        Task task = new Task(name, deadline, duration, description, status);
        Intent intent = new Intent();
        intent.putExtra("task", task);
        setResult(RESULT_OK, intent);
        finish();
    }
}