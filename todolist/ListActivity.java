package com.example.todolist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setTitle("To-Dos List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListFragment todoList = new ListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, todoList).commit();

    }
}