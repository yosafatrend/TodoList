package com.yorren.todolist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    List<ToDo> list = new ArrayList<ToDo>();
    TodoAdapter toDoAdapter;
    DatabaseHelper myDb;
    RecyclerView rvList;

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
        toDoAdapter = new TodoAdapter(getActivity(), list);

        list.addAll(myDb.getAllData());
        toDoAdapter.notifyDataSetChanged();
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()   ));
        rvList.setAdapter(toDoAdapter);

        return view;
    }
}