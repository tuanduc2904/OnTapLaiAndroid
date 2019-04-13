package com.example.nguyenduc.todolist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenduc.todolist.model.ToDo;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class AdapterListView extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private ArrayList<ToDo> toDos;

    public AdapterListView(MainActivity context, int layout, ArrayList<ToDo> toDos) {
        this.context = context;
        this.layout = layout;
        this.toDos = toDos;
    }

    @Override
    public int getCount() {
        return toDos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    private class ViewHolder{
        TextView tv_MSSV,tv_Name;
        Button bt_Edit,bt_Delete;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolder= new ViewHolder();

            viewHolder.tv_Name = convertView.findViewById(R.id.tv_Name);
            viewHolder.tv_MSSV = convertView.findViewById(R.id.tv_MSSV);
            viewHolder.bt_Delete = convertView.findViewById(R.id.bt_Delete_Item);
            viewHolder.bt_Edit=convertView.findViewById(R.id.bt_Edit_Item);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final ToDo toDo = toDos.get(position);
        viewHolder.tv_MSSV.setText(toDo.getMssv());
        viewHolder.tv_Name.setText(toDo.getName());

        viewHolder.bt_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.showEditDialog(toDo);
            }
        });
        viewHolder.bt_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.showDeleteDialog(toDo.get_id());
            }
        });

        return convertView;
    }

}
