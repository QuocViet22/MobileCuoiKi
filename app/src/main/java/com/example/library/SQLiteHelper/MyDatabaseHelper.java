package com.example.library.SQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.example.library.DTO.TbBook;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    // SQLiteHelper/CreateDBBook
    public static final String DATABASE_NAME = "BookLibrary.db";
    public static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TbBook.TABLE_NAME +
                " (" + TbBook.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TbBook.COLUMN_TITLE + " TEXT, " +
                TbBook.COLUMN_AUTHOR + " TEXT, " +
                TbBook.COLUMN_PAGES + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TbBook.TABLE_NAME);
        onCreate(db);
    }

    public void addBook(TbBook tbBook) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TbBook.COLUMN_TITLE, tbBook.getBook_title());
        cv.put(TbBook.COLUMN_AUTHOR, tbBook.getBook_author());
        cv.put(TbBook.COLUMN_PAGES, Integer.valueOf(tbBook.getBook_pages()));
        long result = db.insert(TbBook.TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TbBook.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateData(String row_id, TbBook tbBook) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TbBook.COLUMN_TITLE, tbBook.getBook_title());
        cv.put(TbBook.COLUMN_AUTHOR, tbBook.getBook_author());
        cv.put(TbBook.COLUMN_PAGES, tbBook.getBook_pages());

        long result = db.update(TbBook.TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TbBook.TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TbBook.TABLE_NAME);
    }

}
