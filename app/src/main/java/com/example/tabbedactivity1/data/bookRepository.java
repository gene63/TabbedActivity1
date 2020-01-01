package com.example.tabbedactivity1.data;

import androidx.lifecycle.LiveData;

import java.util.List;

public class bookRepository {

    private bookDAO mBookDAO;
    private LiveData<List<bookEntity>> allBookEntities;
}
