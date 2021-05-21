package com.example.todolist;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import android.widget.Toast;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    public ListFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, parent, false);
        DatabaseHelper mDatabaseHelper = new DatabaseHelper(getActivity());

        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1)+ "\n" + data.getString(2));
        }

        ListView listView = (ListView) view.findViewById(R.id.todoList);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                listData
        );

        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String todo = adapterView.getItemAtPosition(i).toString();
                String todoName = todo.split("\\r?\\n")[0];

                Cursor data = mDatabaseHelper.getTodoID(todoName);
                int todoID = -1;
                while(data.moveToNext()){
                    todoID = data.getInt(0);
                }
                if(todoID > -1){
                    Intent editScreenIntent = new Intent(getActivity(), EditDataActivity.class);
                    editScreenIntent.putExtra("id",todoID);
                    editScreenIntent.putExtra("name",todo);
                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that to-do");
                }
            }

            private void toastMessage(String message){
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}