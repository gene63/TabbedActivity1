package com.example.tabbedactivity1.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface bookDAO {
    @Query("SELECT * FROM book_table ORDER BY date")
    List<bookEntity> getAllBookEntity();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(bookEntity data);

    @Update
    void updateData(bookEntity data);

    @Delete
    void deleteData(bookEntity data);
}
