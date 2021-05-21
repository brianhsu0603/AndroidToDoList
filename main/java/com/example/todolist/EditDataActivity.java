package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by User on 2/28/2017.
 */

public class EditDataActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button Save,Delete;
    private EditText Edit_todo, Edit_date;

    DatabaseHelper mDatabaseHelper;

    private String selectedName;
    private String selectedDate;
    private int selectedID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        Save = (Button) findViewById(R.id.save);
        Delete = (Button) findViewById(R.id.delete);
        Edit_todo = (EditText) findViewById(R.id.edit_todo);
        Edit_date = (EditText) findViewById(R.id.edit_date);

        mDatabaseHelper = new DatabaseHelper(this);

        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");

        //set the text to show the current selected name
        Edit_todo.setText(selectedName.split("\\r?\\n")[0]);
        Edit_date.setText(selectedName.split("\\r?\\n")[1]);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todo = Edit_todo.getText().toString();
                String date = Edit_date.getText().toString();

                if(!todo.equals("") && !date.equals("")){
                    mDatabaseHelper.updateTodo(todo,selectedID,selectedName.split("\\r?\\n")[0],date);
                    toastMessage("Successfully updated");
                    Intent intent = new Intent(EditDataActivity.this, ListActivity.class);
                    startActivity(intent);
                }else{
                    toastMessage("You must fill in both fields");
                }
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteTodo(selectedID,selectedName.split("\\r?\\n")[0]);
                toastMessage("removed from list");
                Intent intent = new Intent(EditDataActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
