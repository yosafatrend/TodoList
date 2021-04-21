package com.yorren.todolist;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AddFragment extends Fragment {

    List<ToDo> list = new ArrayList<ToDo>();
    TodoAdapter toDoAdapter;
    DatabaseHelper myDb;
    RecyclerView rvList;
    FloatingActionButton fabAdd;
    Dialog popup;
    EditText edtSearch;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);
        fabAdd = v.findViewById(R.id.fab_add);
        popup = new Dialog(getContext());
        rvList = v.findViewById(R.id.rvList);
        myDb = new DatabaseHelper(getActivity());
        toDoAdapter = new TodoAdapter(getActivity(), list);
        edtSearch = v.findViewById(R.id.edtSearch);

        list.addAll(myDb.getAllData());
        toDoAdapter.notifyDataSetChanged();
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(toDoAdapter);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtSearch.length() != 0){
                    List<ToDo> todoSearch = myDb.search(edtSearch.getText().toString().trim());
                    if (todoSearch != null) {
                        rvList.setAdapter(new TodoAdapter(getActivity(), todoSearch));
                    }
                }else{
                    List<ToDo> todoSearch = myDb.getAllData();
                    if (todoSearch != null) {
                        rvList.setAdapter(new TodoAdapter(getActivity(), todoSearch));
                    }
                }
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setContentView(R.layout.add_popup);
                EditText edtNama = popup.findViewById(R.id.edtNama);
                Button btnAdd = popup.findViewById(R.id.btnAdd);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(edtNama.getText().toString(), "0");

                        popup.dismiss();
                        FragmentTransaction tr = getFragmentManager().beginTransaction();
                        tr.replace(R.id.page_container, new AddFragment());
                        tr.commit();
                    }
                });
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popup.show();
            }
        });

        return v;

    }


}