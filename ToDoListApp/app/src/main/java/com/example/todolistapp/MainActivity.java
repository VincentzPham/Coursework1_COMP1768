// MainActivity.java
package com.example.todolistapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnItemClickListener {
    private ArrayList<Task> taskList;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskList = new ArrayList<>();

        RecyclerView recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TaskAdapter(this, taskList, this);
        recyclerViewTasks.setAdapter(adapter);

        FloatingActionButton btnAddTask = findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Task task = (Task) data.getSerializableExtra("task");
            int position = data.getIntExtra("position", -1);

            if (requestCode == 1) { // Add Task
                taskList.add(task);
            } else if (requestCode == 2 && position >= 0) { // Edit/Delete Task
                if (data.getBooleanExtra("delete", false)) {
                    taskList.remove(position);
                } else {
                    taskList.set(position, task);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, EditDeleteTaskActivity.class);
        intent.putExtra("task", taskList.get(position));
        intent.putExtra("position", position);
        startActivityForResult(intent, 2);
    }
}
