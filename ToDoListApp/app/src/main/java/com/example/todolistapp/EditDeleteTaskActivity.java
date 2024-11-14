package com.example.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditDeleteTaskActivity extends AppCompatActivity {
    private EditText editTaskName, editTaskDeadline, editTaskDuration, editTaskDescription;
    private Task task;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_task);

        editTaskName = findViewById(R.id.editTaskName);
        editTaskDeadline = findViewById(R.id.editTaskDeadline);
        editTaskDuration = findViewById(R.id.editTaskDuration);
        editTaskDescription = findViewById(R.id.editTaskDescription);
        Button btnSaveEditTask = findViewById(R.id.btnSaveEditTask);
        Button btnDeleteTask = findViewById(R.id.btnDeleteTask);

        task = (Task) getIntent().getSerializableExtra("task");
        position = getIntent().getIntExtra("position", -1);

        editTaskName.setText(task.getName());
        editTaskDeadline.setText(task.getDeadline());
        editTaskDuration.setText(String.valueOf(task.getDuration()));
        editTaskDescription.setText(task.getDescription());

        btnSaveEditTask.setOnClickListener(v -> saveChanges());
        btnDeleteTask.setOnClickListener(v -> deleteTask());
    }

    private void saveChanges() {
        task.setName(editTaskName.getText().toString());
        task.setDeadline(editTaskDeadline.getText().toString());
        task.setDuration(Integer.parseInt(editTaskDuration.getText().toString()));
        task.setDescription(editTaskDescription.getText().toString());

        Intent intent = new Intent();
        intent.putExtra("task", task);
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        finish();
    }

//    private void deleteTask() {
//        Intent intent = new Intent();
//        intent.putExtra("position", position);
//        intent.putExtra("delete", true);
//        setResult(RESULT_OK, intent);
//        finish();
//    }

    private void deleteTask() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent();
                    intent.putExtra("position", position);
                    intent.putExtra("delete", true);
                    setResult(RESULT_OK, intent);
                    finish();
                    // Show a toast after deleting
                    Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

}