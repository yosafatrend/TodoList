package com.yorren.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {
    Context context;
    List<ToDo> todo;

    public TodoAdapter(Context context, List<ToDo> todo) {
        this.context = context;
        this.todo = todo;
    }

    @NonNull
    @Override
    public TodoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.todo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.MyViewHolder holder, int position) {
        holder.cbName.setText(todo.get(position).getNama());
    }

    @Override
    public int getItemCount() {
        return todo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cbName = itemView.findViewById(R.id.cb_name);
        }
    }
}
