package com.example.library.Login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import com.example.library.DTO.TbBook;

public class LoginDB extends SQLiteOpenHelper {
    public static final String DBNAME = "BookLibraryUser.db";
    private Context context;

    public LoginDB(Context context) {
        super(context, "BookLibraryUser.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table if not exists users(username TEXT primary key, password TEXT, image Blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username, String password, byte[] image) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("image", image);

//        String sql = "Insert into users values (?,?,?)";
//        SQLiteStatement statement = MyDB.compileStatement(sql);
//        statement.clearBindings();
//        statement.bindString(0, username);
//        statement.bindString(1, password);
//        statement.bindBlob(2, image);
//        long result = statement.executeInsert();

        long result = MyDB.insert("users", null, contentValues);
        if (result == -1) return false;
        else
            return true;
    }

    public Boolean updateData(String username, String password, byte[] image) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("image", image);

//        String sql = "Insert into users values (?,?,?)";
//        SQLiteStatement statement = MyDB.compileStatement(sql);
//        statement.clearBindings();
//        statement.bindString(0, username);
//        statement.bindString(1, password);
//        statement.bindBlob(2, image);
//        long result = statement.executeInsert();

        long result = MyDB.update("users", contentValues, "username=?", new String[]{username});
        if (result == -1) return false;
        else
            return true;
    }


    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Cursor listUser() {
        String query = "SELECT * FROM users";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    public void deleteOneRow(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(User.TABLE_NAME, "userName=?", new String[]{userName});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }
}
