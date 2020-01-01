package com.example.tabbedactivity1.data;

import android.content.Context;

import androidx.room.Room;

public class dbClient {

    private Context mCtx;
    private static dbClient mInstance;

    //our app database object
    private bookDatabase bookDB;

    private dbClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        bookDB = Room.databaseBuilder(mCtx, bookDatabase.class, "book_database")
                .allowMainThreadQueries()
                .build();
    }

    public static synchronized dbClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new dbClient(mCtx);
        }
        return mInstance;
    }

    public bookDatabase getBookDB() {
        return bookDB;
    }
}
