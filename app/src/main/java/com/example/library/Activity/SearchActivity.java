package com.example.library.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.library.Adapter.BookAdapter;
import com.example.library.DTO.TbBook;
import com.example.library.R;
import com.example.library.SQLiteHelper.MyDatabaseHelper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {

    private RecyclerView rcvBooks;
    private BookAdapter bookAdapter;
    private SearchView searchView;
    MyDatabaseHelper myDB;
    String _id, book_title, book_author, book_pages;
    ImageView empty_imageview;
    TextView no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rcvBooks = findViewById(R.id.rcv_books);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvBooks.setLayoutManager(linearLayoutManager);

        bookAdapter = new BookAdapter(getListBooks());

        rcvBooks.setAdapter(bookAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvBooks.addItemDecoration(itemDecoration);
    }

    //    set data
    private List<TbBook> getListBooks() {
        List<TbBook> list = new ArrayList<>();
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        myDB = new MyDatabaseHelper(SearchActivity.this);
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                _id = cursor.getString(0);
                book_title = cursor.getString(1);
                book_author = cursor.getString(2);
                book_pages = cursor.getString(3);
//                Log.i("In ra", book_title);
                list.add(new TbBook(R.drawable.book, _id, book_title, book_author, book_pages));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }

        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bookAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bookAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}