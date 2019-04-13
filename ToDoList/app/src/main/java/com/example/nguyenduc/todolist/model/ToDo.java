package com.example.nguyenduc.todolist.model;

public class ToDo {
    private int _id;
    private String name;
    private String mssv;

    public ToDo() {
    }

    public ToDo(int _id, String name, String mssv) {
        this._id = _id;
        this.name = name;
        this.mssv = mssv;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }
}
