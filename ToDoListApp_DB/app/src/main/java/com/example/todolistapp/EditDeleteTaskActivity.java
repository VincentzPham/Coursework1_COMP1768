package com.example.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class EditDeleteTaskActivity extends AppCompatActivity {
    private EditText editTaskName, editTaskDeadline, editTaskDuration, editTaskDescription;
    private Task task;
    private int position;
    private Spinner spinnerTaskStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_task);

        editTaskName = findViewById(R.id.editTaskName);
        editTaskDeadline = findViewById(R.id.editTaskDeadline);
        editTaskDuration = findViewById(R.id.editTaskDuration);
        editTaskDescription = findViewById(R.id.editTaskDescription);

        spinnerTaskStatus = findViewById(R.id.spinnerTaskStatus);

        // Thiết lập Spinner với các giá trị từ mảng trong strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.task_status_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskStatus.setAdapter(adapter);

//        Button btnSaveEditTask = findViewById(R.id.btnSaveEditTask);
//        Button btnDeleteTask = findViewById(R.id.btnDeleteTask);

        task = (Task) getIntent().getSerializableExtra("task");
        position = getIntent().getIntExtra("position", -1);

        editTaskName.setText(task.getName());
        editTaskDeadline.setText(task.getDeadline());
        editTaskDuration.setText(String.valueOf(task.getDuration()));
        editTaskDescription.setText(task.getDescription());

        if (task.getStatus() != null) {
            int spinnerPosition = adapter.getPosition(task.getStatus());
            spinnerTaskStatus.setSelection(spinnerPosition);
        }

//        btnSaveEditTask.setOnClickListener(v -> saveChanges());
//        btnDeleteTask.setOnClickListener(v -> deleteTask());

        Button btnSaveEditTask = findViewById(R.id.btnSaveEditTask);
        btnSaveEditTask.setOnClickListener(v -> saveChanges());

        Button btnDeleteTask = findViewById(R.id.btnDeleteTask);
        btnDeleteTask.setOnClickListener(v -> deleteTask());
    }

    private void saveChanges() {
        task.setName(editTaskName.getText().toString());
        task.setDeadline(editTaskDeadline.getText().toString());
        task.setDuration(Integer.parseInt(editTaskDuration.getText().toString()));
        task.setDescription(editTaskDescription.getText().toString());
        task.setStatus(spinnerTaskStatus.getSelectedItem().toString());

        Intent intent = new Intent();
        intent.putExtra("task", task);
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void deleteTask() {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.putExtra("delete", true);
        setResult(RESULT_OK, intent);
        finish();
    }
}