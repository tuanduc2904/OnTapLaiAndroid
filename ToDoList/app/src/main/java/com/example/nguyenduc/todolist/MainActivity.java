package com.example.nguyenduc.todolist;

import android.app.Dialog;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nguyenduc.todolist.database.Database;
import com.example.nguyenduc.todolist.model.ToDo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Database db;
    private EditText ed_Name;
    private Button bt_Search;
    private Button bt_CancelSearch;
    private ListView lv_Todo;
    private ArrayList<ToDo> toDos;
    private AdapterListView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        bt_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value =ed_Name.getText().toString();
                toDos.clear();
                toDos.addAll(db.searchTodo(value));
                adapter.notifyDataSetChanged();
            }
        });
        bt_CancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void init() {
        db = new Database(this);
        ed_Name= findViewById(R.id.ed_Name);
        bt_Search =findViewById(R.id.bt_Search);
        bt_CancelSearch=findViewById(R.id.bt_CancelSearch);
        lv_Todo=findViewById(R.id.lv_Todo);
        toDos= db.get_Todo();
        adapter = new AdapterListView( MainActivity.this,R.layout.item_listview,toDos);
        lv_Todo.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menu_Add){
            dialog_Add();
        }
        return super.onOptionsItemSelected(item);
    }
    private void dialog_Add(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add);

        Button bt_Cancel = dialog.findViewById(R.id.bt_Cancel_Dialog);
        Button bt_Add = dialog.findViewById(R.id.bt_Add_Dialog);
         final EditText ed_Name_Dialog = dialog.findViewById(R.id.ed_Name_Dialog);
         final EditText ed_MSSV_Dialog = dialog.findViewById(R.id.ed_MSSV_Dialog);

        bt_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        bt_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ed_Name_Dialog.getText().toString();
                String mssv = ed_MSSV_Dialog.getText().toString();
                if (name.equals("")){
                    Toast.makeText(MainActivity.this, "Vui long nhap ten", Toast.LENGTH_SHORT).show();
                }
                else if(mssv.equals("")){
                    Toast.makeText(MainActivity.this, "Vui long nhap mssv", Toast.LENGTH_SHORT).show();
                }
                else {
                    ContentValues values = new ContentValues();
                    values.put("name",name);
                    values.put("mssv",mssv);
                    db.add_Todo(values);
                    Toast.makeText(MainActivity.this, "Da them", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    updateData();
                }
            }
        });

        dialog.show();

    }
    public void showEditDialog(final ToDo toDo){
        final Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit);
        final EditText ed_EditName = dialog.findViewById(R.id.ed_EditName);
        final EditText ed_EditMSSV = dialog.findViewById(R.id.ed_EditMSSV);
        Button bt_SaveEdit = dialog.findViewById(R.id.bt_SaveEdit);
        Button bt_CancelEdit = dialog.findViewById(R.id.bt_CancelEdit);

        ed_EditMSSV.setText(toDo.getMssv());
        ed_EditName.setText(toDo.getName());
        bt_CancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        bt_SaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("mssv",ed_EditMSSV.getText().toString());
                values.put("name",ed_EditName.getText().toString());
                db.upDateToDo(toDo.get_id(),values);
                dialog.dismiss();
                updateData();
            }
        });

        dialog.show();

    }
    private void updateData(){
        toDos.clear();
        toDos.addAll(db.get_Todo());
        adapter.notifyDataSetChanged();
    }

    public void showDeleteDialog(final int id) {
        final Dialog dialog =new Dialog(this);
        dialog.setContentView(R.layout.dialog_remove);
        Button btCancelRM = dialog.findViewById(R.id.bt_CancelDelete);
        Button btRemove = dialog.findViewById(R.id.bt_DELETEITEM);
        btCancelRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.removeToDo(id);
                updateData();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
