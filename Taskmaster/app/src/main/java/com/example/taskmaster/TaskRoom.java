package com.example.taskmaster;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

//import com.amplifyframework.datastore.generated.model.State;

@Entity(tableName = "tasks")
public class TaskRoom {

    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    String body;
    State state;
    String file = null;

    public TaskRoom(String title, String body, State state, String file) {
        this.title = title;
        this.body = body;
        this.state = state;
        this.file = file;
    }
//
//    public TaskRoom(String title, String body, State state) {
//        this.title = title;
//        this.body = body;
//        this.state = state;

//    }


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public TaskRoom() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
