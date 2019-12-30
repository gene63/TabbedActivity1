package com.example.tabbedactivity1.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {bookEntity.class}, version=1)

public abstract class bookDatabase extends RoomDatabase {
    public abstract bookDAO bookDAO();

    private static bookDatabase INSTANCE;

    public static bookDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (bookDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            bookDatabase.class,
                            "book_database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
