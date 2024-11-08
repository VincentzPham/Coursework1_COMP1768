// TaskAdapter.java
package com.example.todolistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context context;
    private ArrayList<Task> tasks;
    private OnItemClickListener listener;

    public TaskAdapter(Context context, ArrayList<Task> tasks, OnItemClickListener listener) {
        this.context = context;
        this.tasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.tvTaskName.setText(task.getName());
        holder.tvTaskDeadline.setText("Deadline: " + task.getDeadline());
        holder.tvTaskDuration.setText("Duration: " + task.getDuration() + " hours");
        holder.tvTaskDescription.setText("Description: " + task.getDescription());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskName, tvTaskDeadline, tvTaskDuration, tvTaskDescription;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            tvTaskDeadline = itemView.findViewById(R.id.tvTaskDeadline);
            tvTaskDuration = itemView.findViewById(R.id.tvTaskDuration);
            tvTaskDescription = itemView.findViewById(R.id.tvTaskDescription);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
