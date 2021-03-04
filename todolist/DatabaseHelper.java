package com.example.todolist;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by User on 2/28/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "todo_table";
    private static final String COL1 = "id";
    private static final String COL2 = "todo";
    private static final String COL3 = "date";


    public DatabaseHelper(Context context) {
        super(context, "todo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " + COL3 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(int id, String todo, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL1, id);
        contentValues.put(COL2, todo);
        contentValues.put(COL3, date);

        long result = db.insert(TABLE_NAME, null, contentValues);
//if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor getTodoID(String todo){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + todo + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    public void updateTodo(String newTodo, int id, String oldTodo, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query1 = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newTodo + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldTodo + "'";
        db.execSQL(query1);
        String query2 = "UPDATE " + TABLE_NAME + " SET " + COL3 +
                " = '" + date + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldTodo + "'";
        db.execSQL(query2);

    }


    public void deleteTodo(int id, String todo){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + todo + "'";

        db.execSQL(query);
    }
}

