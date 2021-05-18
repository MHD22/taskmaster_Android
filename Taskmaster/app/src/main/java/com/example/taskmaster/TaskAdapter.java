package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    List<Task> tasks = new ArrayList<>();
    Context context;

    public TaskAdapter(List<Task> tasks, Context context) {
        this.context = context;
        this.tasks = tasks;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{


        TextView titleT, bodyT, stateT;
        View itemView;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            titleT = itemView.findViewById(R.id.fragment_title_text);
            bodyT = itemView.findViewById(R.id.fragment_body_text);
            stateT = itemView.findViewById(R.id.fragment_state_text);
        }
    }


    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task,parent,false);

       return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.titleT.setText(task.title);
        holder.bodyT.setText(task.body);
        holder.stateT.setText(task.state.toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent taskDetailsIntent = new Intent(context, TaskDetailActivity.class);
                taskDetailsIntent.putExtra("title",task.title);
                context.startActivity(taskDetailsIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
