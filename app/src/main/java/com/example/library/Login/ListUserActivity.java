package com.example.library.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.library.Adapter.UserAdapter;
import com.example.library.R;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {

    ArrayList<User> arrayUser;
    ListView lvDanhsach;

//        Cursor cursor = MainActivity.db.TruyVanTraVe("Select * from SanPham");
    LoginDB DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        lvDanhsach = (ListView) findViewById(R.id.lvDanhsach);
        DB = new LoginDB(ListUserActivity.this);
        Cursor cursor = DB.listUser();

        Log.i("In ra", "a");
        arrayUser = new ArrayList<>();
        while (cursor.moveToNext()) {
            String userName = cursor.getString(0);
            String password = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            arrayUser.add(new User(userName, password, image));
        }
        UserAdapter adapter = new UserAdapter(ListUserActivity.this, ListUserActivity.this, R.layout.row_listview, arrayUser);
        lvDanhsach.setAdapter(adapter);
    }
}