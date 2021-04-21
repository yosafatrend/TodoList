package com.yorren.todolist;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    List<ToDo> list = new ArrayList<ToDo>();
    TaskAdapter taskAdapter;
    DatabaseHelper myDb;
    RecyclerView rvList;
    EditText edtSearch;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvList = view.findViewById(R.id.rvList);
        myDb = new DatabaseHelper(getActivity());
        taskAdapter = new TaskAdapter(getActivity(), list);
        edtSearch = view.findViewById(R.id.edtSearch);

        list.addAll(myDb.getDataUnchecked());
        taskAdapter.notifyDataSetChanged();
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(taskAdapter);

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
                    List<ToDo> todoSearch = myDb.searchChecked(edtSearch.getText().toString().trim());
                    if (todoSearch != null) {
                        rvList.setAdapter(new TodoAdapter(getActivity(), todoSearch));
                    }
                }else{
                    List<ToDo> todoSearch = myDb.getDataUnchecked();
                    if (todoSearch != null) {
                        rvList.setAdapter(new TodoAdapter(getActivity(), todoSearch));
                    }
                }
            }
        });

        return view;
    }
}