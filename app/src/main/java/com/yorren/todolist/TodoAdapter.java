package com.yorren.todolist;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {
    //mendeklarasikan variabel yang dibutuhkan
    Context context;
    List<ToDo> todo;
    DatabaseHelper myDb;
    Dialog popup;

    public TodoAdapter(Context context, List<ToDo> todo) {
        //mendapatkan value yang dikirim dan mengisinya ke variabel kita
        this.context = context;
        this.todo = todo;
        myDb = new DatabaseHelper(context);
        popup = new Dialog(context);
    }

    @NonNull
    @Override
    public TodoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //menetapkan layout yang akan digunakan
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.todo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.MyViewHolder holder, int position) {
        //menetapkan text yang akan ditampilkan oleh komponen cbName
        holder.cbName.setText(todo.get(position).getNama());
        //mendapatkan id dari item
        String id = todo.get(position).getId();
        //jika id dari item sama dengan 0
        if (todo.get(position).getStatus().equals("0")){
            //menghilangkan centang checkbox
            holder.cbName.setChecked(false);
        }
        //jika sama dengan 1
        else if (todo.get(position).getStatus().equals("1")){
            //mencentang checkbox
            holder.cbName.setChecked(true);
        }
        //trigger ketika checkbox diklik
        holder.cbName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //jika cbName dicentang maka
                if (holder.cbName.isChecked()){
                    //memanggil fungsi update data dari database helper dan menampung return nya ke variabel isUpdated
                    boolean isUpdated = myDb.updateData(todo.get(position).getNama(), "1", id);
                    //jika isUpdate true
                    if (isUpdated){
                        //menampilkan toast sukses
                        Toast.makeText(context, "Sukses", Toast.LENGTH_SHORT).show();
                    }else{
                        //menampilkan toast gagal
                        Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
                //jika tidak dicentang
                else{
                    //memanggil fungsi update data dari database helper dan menampung return nya ke variabel isUpdated
                    boolean isUpdated = myDb.updateData(todo.get(position).getNama(), "0", id);
                    //jika isUpdate true
                    if (isUpdated){
                        //menampilkan toast sukses
                        Toast.makeText(context, "Sukses", Toast.LENGTH_SHORT).show();
                    }else{
                        //menampilkan toast gagal
                        Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //trigger ketika item diklik
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menetapkan layout popup
                popup.setContentView(R.layout.edit_popup);
                //mendeklarasikan komponen view
                EditText edtNama = popup.findViewById(R.id.edtNama);
                Button btnEdit = popup.findViewById(R.id.btnEdit);
                Button btnDelete = popup.findViewById(R.id.btnDelete);
                //menetapkan konten edit text
                edtNama.setText(todo.get(position).getNama());
                //trigger ketika button edit diklik dan mengupdatenya
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDb.updateData(edtNama.getText().toString(), todo.get(position).getStatus(), id);
                        if (isUpdated){
                            Toast.makeText(context, "Sukses", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                        popup.dismiss();
                        ((MainActivity)context).getFragmentPage(new HistoryFragment());
                    }
                });
                //trigger klik untuk menghapus data
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(todo.get(position).getId());
                        popup.dismiss();
                        ((MainActivity)context).getFragmentPage(new HistoryFragment());
                    }
                });
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //menampilkan popup
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        //menetapkan jumlah item yang akan ditampilkan
        return todo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //mendeklarasikan komponen view dari item
        CheckBox cbName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        //menginisialisasi komponen view dari item
            cbName = itemView.findViewById(R.id.cb_name);
        }
    }
}
