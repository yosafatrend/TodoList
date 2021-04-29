package com.yorren.todolist;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    //SAMA SEPERTI YANG ADA DI TODOADAPTER
    Context context;
    List<ToDo> todo;
    DatabaseHelper myDb;
    Dialog popup;

    public TaskAdapter(Context context, List<ToDo> todo) {
        this.context = context;
        this.todo = todo;
        myDb = new DatabaseHelper(context);
        popup = new Dialog(context);
    }

    @NonNull
    @Override
    public TaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.todo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.MyViewHolder holder, int position) {
        holder.cbName.setText(todo.get(position).getNama());

        String id = todo.get(position).getId();
        if (todo.get(position).getStatus().equals("0")){
            holder.cbName.setChecked(false);
        }else if (todo.get(position).getStatus().equals("1")){
            holder.cbName.setChecked(true);
        }

        holder.cbName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.cbName.isChecked()){
                    holder.cbName.setChecked(true);
                    boolean isUpdated = myDb.updateData(todo.get(position).getNama(), "1", id);

                }else{
                    holder.cbName.setChecked(false);
                    boolean isUpdated = myDb.updateData(todo.get(position).getNama(), "0", id);

                }
            }
        });
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
