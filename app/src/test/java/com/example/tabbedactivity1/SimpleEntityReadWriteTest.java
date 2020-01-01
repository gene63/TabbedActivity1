package com.example.tabbedactivity1;

import android.content.Context;

import androidx.room.Room;

import java.io.IOException;
import java.util.List;

import com.example.tabbedactivity1.data.bookDAO;
import com.example.tabbedactivity1.data.bookDatabase;
import com.example.tabbedactivity1.data.bookEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEntityReadWriteTest{
    private com.example.tabbedactivity1.data.bookDAO bookDAO;
    private bookDatabase db;

    @Mock
    Context mockContext;

    @Before
    public void createDb() {

        db = Room.inMemoryDatabaseBuilder(mockContext, bookDatabase.class).build();
        bookDAO = db.bookDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        bookEntity bookEntity = new bookEntity();
        bookEntity.setValue("1234");
        bookEntity.setType("inc");
        bookEntity.setDate("2020.01.01");
        bookDAO.insertData(bookEntity);
        List<bookEntity> books = bookDAO.getAllBookEntity();
        assertEquals(books.get(0), bookEntity);
    }
}