package com.example.todolist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DatabaseHelper mDatabaseHelper;
    private Button Add, TDList;
    private EditText ToDo;
    private TextView DDL;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static String date = "";
    private static int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Just Do It");
        ToDo = (EditText) findViewById(R.id.todo);
        DDL = (TextView) findViewById(R.id.deadline);
        Add = (Button) findViewById(R.id.add);
        TDList = (Button) findViewById(R.id.tdlist);
        mDatabaseHelper = new DatabaseHelper(this);

        DDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date = month + "/" + day + "/" + year;
                DDL.setText(date);
            }
        };


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todo = ToDo.getText().toString();

                if (ToDo.length() != 0 && date.length() != 0) {
                    AddData(id,todo,date);
                    ToDo.setText("");
                    DDL.setText("Choose a date");
                    date = "";
                    id += 1;
                } else {
                    toastMessage("You must enter a to-do and a date!");
                }

            }
        });

        TDList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });


    }

    public void AddData(int id, String todo, String date) {
        ToDo newTodo = new ToDo(id,todo,date);
        boolean insertData = mDatabaseHelper.addData(id,todo,date);

        if (insertData) {
            toastMessage("Successfully added to to-dos list!");
        } else {
            toastMessage("Something went wrong, please try again.");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}