package com.example.tabbedactivity1.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {bookEntity.class}, version=1)

public abstract class bookDatabase extends RoomDatabase {
    public abstract bookDAO bookDAO();
}
