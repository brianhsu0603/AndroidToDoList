package com.example.todolist;

public class ToDo {
    private int id;
    private String todo;
    private String date;


    public ToDo (int id, String todo, String date){
        this.id = id;
        this.todo = todo;
        this.date = date;

    }

    public int getId() { return id;}
    public String getToDo() {
        return todo;
    }
    public String getDate() {
        return date;
    }

    public void setId(int id) {this.id = id; }
    public void setToDo(String todo) {
        this.todo = todo;
    }
    public void setDate(String date) {
        this.date = date;
    }


}