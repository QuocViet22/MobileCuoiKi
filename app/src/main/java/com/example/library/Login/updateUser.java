package com.example.library.Login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.Activity.MainActivity;
import com.example.library.Activity.UpdateActivity;
import com.example.library.DTO.TbBook;
import com.example.library.R;
import com.example.library.SQLiteHelper.MyDatabaseHelper;

import java.io.ByteArrayOutputStream;

public class updateUser extends AppCompatActivity {

    Button btnUpdate, btnDelete;
    EditText password_input, repassword_input;
    TextView username_input;
    ImageView image_input;
    String userName, password, repassword, title, bookShelf;
    RadioGroup radioGroup;
    RadioButton radioButton;
    byte[] avatar;
    private int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        repassword_input = findViewById(R.id.repassword);
        password_input = findViewById(R.id.password);
        username_input = findViewById(R.id.username);
        image_input = findViewById(R.id.imageView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        radioGroup = findViewById(R.id.radioGroup);
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        getAndSetIntentData();

        image_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                //And only then we call this
                LoginDB DB = new LoginDB(updateUser.this);
                userName = username_input.getText().toString().trim();
                password = password_input.getText().toString().trim();
                repassword = repassword_input.getText().toString().trim();
                bookShelf = (String) radioButton.getText();

                if (userName.equals("") || password.equals("") || repassword.equals(""))
                    Toast.makeText(updateUser.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    if (password.equals(repassword)) {
                        Boolean checkuser = DB.checkusername(userName);
//                        if (checkuser == false) {
//
//                        } else {
//                            Toast.makeText(updateUser.this, "User already exists!", Toast.LENGTH_SHORT).show();
//                        }
                        Boolean updateData = DB.updateData(userName, password, bookShelf, ConverttoArrayByte(image_input));
//                              Boolean updateData = DB.insertData(userName, password, ConverttoArrayByte(image_input));
                        if (updateData == true) {
                            Toast.makeText(updateUser.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(updateUser.this, ListUserActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(updateUser.this, "Updated failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(updateUser.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = username_input.getText().toString().trim();
                confirmDialog(userName);
            }
        });

    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("userName") && getIntent().hasExtra("image")) {
            //Getting Data from Intent
            userName = getIntent().getStringExtra("userName");
            avatar = getIntent().getByteArrayExtra("image");
            bookShelf = getIntent().getStringExtra("bookShelf");

            //Setting Intent Data
            username_input.setText(userName);
//            bookShelf_input.setText(bookShelf);
//            radioButton.set
            Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
            image_input.setImageBitmap(bitmap);
//            Log.d("stev", title + " " + author + " " + pages);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    public byte[] ConverttoArrayByte(ImageView img) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            image_input.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void confirmDialog(String userName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + userName + " ?");
        builder.setMessage("Are you sure you want to delete " + userName + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LoginDB myDB = new LoginDB(updateUser.this);
                myDB.deleteOneRow(userName);
                //Refresh Activity
                Intent intent = new Intent(updateUser.this, ListUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

//        Toast.makeText(this, "Selected Radio Button: " + radioButton.getText(),
//                Toast.LENGTH_SHORT).show();
    }
}