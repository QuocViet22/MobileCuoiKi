package com.example.library.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.library.R;
import com.example.library.SQLiteHelper.MyDatabaseHelper;
import com.example.library.DTO.TbBook;

public class AddActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input;
    Button add_button;

    String title, author;
    int pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        pages_input = findViewById(R.id.pages_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TbBook tbBook = new TbBook();
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                title = title_input.getText().toString().trim();
                author = author_input.getText().toString().trim();
                pages = Integer.valueOf(pages_input.getText().toString().trim());
                tbBook.setBook_title(title);
                tbBook.setBook_author(author);
                tbBook.setBook_pages(String.valueOf(pages));
                myDB.addBook(tbBook);
            }
        });
    }
}
