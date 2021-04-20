package com.yorren.todolist;

public class ToDo {
    String id;
    String nama;
    String status;

    public ToDo() {
    }

    public ToDo(String id, String nama, String status) {
        this.id = id;
        this.nama = nama;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
