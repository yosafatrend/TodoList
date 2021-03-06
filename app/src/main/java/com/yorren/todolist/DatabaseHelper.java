package com.yorren.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //nama database
    public static final String DATABASE_NAME = "Todo.db";

    //nama table
    public static final String TABLE_NAME = "todo_table";

    //versi database
    private static final int DATABASE_VERSION = 1;

    //table column
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAMA";
    public static final String COL_3 = "STATUS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //membuat tabel baru bernama todo_table
        db.execSQL("create table todo_table(id integer primary key autoincrement," +
                "nama text," +
                "status integer);");
    }

    //untuk mengubah struktur database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    //fungsi untuk mengambil semua data di tabel todo_table
    public List<ToDo> getAllData() {
        List<ToDo> todos = new ArrayList<>();
        String selectQuery = "SELECT * FROM TODO_TABLE";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ToDo todo = new ToDo();
                todo.setId(cursor.getString(0));
                todo.setNama(cursor.getString(1));
                todo.setStatus(cursor.getString(2));
                todos.add(todo);
            } while (cursor.moveToNext());
        }

        db.close();
        return todos;
    }


    //fungsi untuk mengambil data yang mempunyai status 0
    public List<ToDo> getDataUnchecked() {
        List<ToDo> todos = new ArrayList<>();
        String selectQuery = "SELECT * FROM TODO_TABLE WHERE STATUS = '0'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ToDo todo = new ToDo();
                todo.setId(cursor.getString(0));
                todo.setNama(cursor.getString(1));
                todo.setStatus(cursor.getString(2));
                todos.add(todo);
            } while (cursor.moveToNext());
        }

        db.close();
        return todos;
    }

    //fungsi untuk mencari data berdasarkan keyword
    public List<ToDo> search(String keyword) {
        List<ToDo> todos = new ArrayList<>();
        String selectQuery = "SELECT * FROM TODO_TABLE WHERE NAMA LIKE ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{"%" + keyword + "%"});
        if (cursor.moveToFirst()) {
            do {
                ToDo todo = new ToDo();
                todo.setId(cursor.getString(0));
                todo.setNama(cursor.getString(1));
                todo.setStatus(cursor.getString(2));
                todos.add(todo);
            } while (cursor.moveToNext());
        }
        db.close();
        return todos;
    }

    //fungsi untuk mencari data dari todo_table yang memiliki status 0 berdasarkan keyword
    public List<ToDo> searchUnchecked(String keyword) {
        List<ToDo> todos = new ArrayList<>();
        String selectQuery = "SELECT * FROM TODO_TABLE WHERE STATUS = '0' AND NAMA LIKE ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{"%" + keyword + "%"});
        if (cursor.moveToFirst()) {
            do {
                ToDo todo = new ToDo();
                todo.setId(cursor.getString(0));
                todo.setNama(cursor.getString(1));
                todo.setStatus(cursor.getString(2));
                todos.add(todo);
            } while (cursor.moveToNext());
        }
        db.close();
        return todos;
    }



    //fungsi untuk menambahkan data
    public boolean insertData(String nama, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nama);
        contentValues.put(COL_3, status);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //fungsi untuk memperbarui data
    public boolean updateData(String nama, String status, String id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, nama);
        contentValues.put(COL_3, status);

        long result = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //fungsi untuk menghapus data
    public int deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
