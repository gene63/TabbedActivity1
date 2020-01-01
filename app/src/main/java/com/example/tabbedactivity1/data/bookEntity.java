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
    private int value;

    @ColumnInfo(name="date")
    private int date;

    @ColumnInfo(name="category")
    private String category;

    // getter and setter
    public int getUid() {
        return uid;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public int getDate() {
        return date;
    }

    public String getCategory() { return category; }

    public void setUid(int id) {
        this.uid = id;
    }

    public void setType(String str) { this.type = str; }

    public void setValue(int value) {
        this.value = value;
    }

    public void setDate(int date) { this.date = date; }

    public void setCategory(String str) { this.category = str; }

}
