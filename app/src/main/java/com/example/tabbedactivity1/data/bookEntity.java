package com.example.tabbedactivity1.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="book_table")
public class bookEntity {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name="type")
    private String type;

    @ColumnInfo(name="value")
    private String value;

    @ColumnInfo(name="date")
    private String date;

    // getter and setter
    public int getUid() {
        return uid;
    }
    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }

    public void setUid(int id) {
        this.uid = id;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public void setDate(String str) {
        this.date = str;
    }

}
