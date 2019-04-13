package com.example.nguyenduc.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.nguyenduc.todolist.model.ToDo;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private Context context;
    public Database(Context context) {
        super(context, "db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createToDo = "CREATE TABLE TODO(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,mssv TEXT)";
        db.execSQL(createToDo);
    }

    public void add_Todo(ContentValues values) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert("TODO",null,values);
            db.close();
        } catch (Exception e) {
            Log.e("Err add:", e.toString());
        }

    }

    public  ArrayList<ToDo> get_Todo(){
        ArrayList<ToDo> toDos = new ArrayList<>();
        String select = "SELECT * FROM TODO";
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.rawQuery(select, null)) {
            if (cursor.moveToFirst()) {
                do {
                    ToDo toDo = new ToDo();
                    toDo.set_id(cursor.getInt(0));
                    toDo.setName(cursor.getString(1));
                    toDo.setMssv(cursor.getString(2));
                    toDos.add(toDo);
                } while (cursor.moveToNext());
            }
            db.close();
        }
        catch (Exception e){
            Log.e("get_Todo",e.toString());
            db.close();
        }
        return  toDos;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void upDateToDo(int id, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.update("TODO",values,"_id=?",new String[]{String.valueOf(id)});
            db.close();
        }catch (Exception e){
            Log.e("update err",e.toString());
            db.close();
        }

    }

    public void removeToDo(int id) {
        SQLiteDatabase db= this.getWritableDatabase();
        try{
            db.delete("TODO","_id=?",new String[]{String.valueOf(id)});
            db.close();
        }catch (Exception e)
        {
            Log.e("ERR REMOVE: ",e.toString());
            db.close();
        }
    }

    public ArrayList<ToDo> searchTodo(String value) {
        ArrayList<ToDo> toDos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM TODO WHERE NAME like '%"+value+"%'";
        try{
            Cursor cursor = db.rawQuery(select,null);
            if (cursor.moveToFirst()){
                do {
                    ToDo toDo = new ToDo();
                    toDo.set_id(cursor.getInt(0));
                    toDo.setName(cursor.getString(1));
                    toDo.setMssv(cursor.getString(2));
                    toDos.add(toDo);
                }while (cursor.moveToNext());
                db.close();
            }
        }
        catch (Exception e){
            Log.e("SEARCH ERROR",e.toString());
            db.close();
        }
        return toDos;
    }
}
