package com.example.tabbedactivity1.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="book_table")
public class bookEntity {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name="type")
    public String type;

    @ColumnInfo(name="value")
    public int value;

    @ColumnInfo(name="date")
    public String date;
}
