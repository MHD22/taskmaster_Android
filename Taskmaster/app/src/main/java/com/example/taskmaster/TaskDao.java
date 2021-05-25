package com.example.taskmaster;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("select * from tasks")
    List <TaskRoom> getAll();

    @Query("select * from tasks where id = :id")
    TaskRoom findTaskById(int id);

    @Insert
    void insert(TaskRoom taskRoom);

    @Insert
    void insertAll(TaskRoom... taskRooms);

    @Delete
    int delete(TaskRoom taskRoom);

    @Query("Delete from tasks where title = :title ")
    int deleteTitle(String title);
}
