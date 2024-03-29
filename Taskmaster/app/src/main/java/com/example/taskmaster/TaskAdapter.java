package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

//    List<Task> tasks = new ArrayList<>();
    List<TaskRoom> tasks = new ArrayList<>();
    Context context;

//    public TaskAdapter(List<Task> tasks, Context context) {
//        this.context = context;
//        this.tasks = tasks;
//    }
    public TaskAdapter(List<TaskRoom> tasks, Context context) {
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
//            stateT = itemView.findViewById(R.id.fragment_state_text);
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
//        Task task = tasks.get(position);
        TaskRoom task = tasks.get(position);
        holder.titleT.setText(task.getTitle());
        holder.bodyT.setText(task.getBody());
//        holder.stateT.setText(task.state.toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent taskDetailsIntent = new Intent(context, TaskDetailActivity.class);
                taskDetailsIntent.putExtra("title", task.getTitle());
                taskDetailsIntent.putExtra("body", task.getBody());
                taskDetailsIntent.putExtra("state", task.getState());
                taskDetailsIntent.putExtra("fileName", task.getFile());
                taskDetailsIntent.putExtra("altitude", task.getAltitude());
                taskDetailsIntent.putExtra("longitude", task.getLongitude());
                Log.i("Adapter.. ",task.getAltitude()+"");
                context.startActivity(taskDetailsIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
