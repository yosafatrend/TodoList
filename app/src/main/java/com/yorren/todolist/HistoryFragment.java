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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    //mendeklarasikan semua variable yang dibutuhkan
    List<ToDo> list = new ArrayList<ToDo>();
    TodoAdapter toDoAdapter;
    DatabaseHelper myDb;
    RecyclerView rvList;
    FloatingActionButton fabAdd;
    Dialog popup;
    EditText edtSearch;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        //menginisialisasi semua komponen yang dibutuhkan
        fabAdd = v.findViewById(R.id.fab_add);
        popup = new Dialog(getContext());
        rvList = v.findViewById(R.id.rvList);
        myDb = new DatabaseHelper(getActivity());
        toDoAdapter = new TodoAdapter(getActivity(), list);
        edtSearch = v.findViewById(R.id.edtSearch);

        //mengambil data menggunakan fungsi getAllData() di class DatabaseHelper
        list.addAll(myDb.getAllData());
        //mengupdate adapter jika data berubah
        toDoAdapter.notifyDataSetChanged();
        //untuk menetapkan layout rvList
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        //menetapkan adapter dari recycler view
        rvList.setAdapter(toDoAdapter);

        //fungsi trigger ketika teks dari edtSearch berubah
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //fungsi setelah teks diubah
            @Override
            public void afterTextChanged(Editable s) {
                //jika panjang teks di edtSearch tidak 0
                if (edtSearch.length() != 0){
                    //mengambil data menggunakan fungsi search di DatabaseHelper
                    List<ToDo> todoSearch = myDb.search(edtSearch.getText().toString().trim());
                    //jika list todoSearch tidak kosong maka
                    if (todoSearch != null) {
                        //menetapkan adapter yang berisi data baru dari list todoSearch
                        rvList.setAdapter(new TodoAdapter(getActivity(), todoSearch));
                    }
                }
                //jika panjang teks di edtSearch 0
                else{
                    //mengambil data menggunakan fungsi getAllData() di DatabaseHelper
                    List<ToDo> todoAll = myDb.getAllData();
                    //jika list todoAll tidak kosong maka
                    if (todoAll != null) {
                        //menetapkan adapter yang berisi data baru dari list todoAll
                        rvList.setAdapter(new TodoAdapter(getActivity(), todoAll));
                    }
                }
            }
        });

        //trigger click untuk floating button add yang memunculkan popup
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menetapkan layout untuk dialog popup
                popup.setContentView(R.layout.add_popup);
                //mendeklarasikan komponen view di layout popup
                EditText edtNama = popup.findViewById(R.id.edtNama);
                Button btnAdd = popup.findViewById(R.id.btnAdd);

                //fungsi trigger click ketika button add diklik yang digunakan untuk menambah data
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //jika edtNama tidak diisi
                        if (edtNama.getText().equals("")){
                            //menampilkan pesan error
                            edtNama.setError("Kegiatan harus diisi");
                        }
                        //jika diisi
                        else{
                            //menambahkan data kegiatan
                            myDb.insertData(edtNama.getText().toString(), "0");
                            //menutup popup
                            popup.dismiss();
                            //merefresh data fragment
                            FragmentTransaction tr = getFragmentManager().beginTransaction();
                            tr.replace(R.id.page_container, new HistoryFragment());
                            tr.commit();
                        }
                    }
                });
                //menghilangkan background default dari popup
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //menampilkan popup
                popup.show();
            }
        });
        return v;
    }
}