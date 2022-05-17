package com.example.library.Adapter;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.library.Login.User;
import com.example.library.Login.updateUser;
import com.example.library.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(Activity activity, Context context, int resource, List<User> item) {
        super(context, resource, item);
        this.context = context;
        this.activity = activity;
    }

    private Context context;
    private Activity activity;

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.row_listview, null);
        }
        User user = getItem(position);
        if (user != null) {
            CardView cardView = (CardView) view.findViewById(R.id.cardView);
            ImageView imgHinhDaidien = (ImageView) view.findViewById(R.id.imgAnhDaiDien);
            TextView txtTenSP = (TextView) view.findViewById(R.id.txtTen);
            TextView txtShelf = view.findViewById(R.id.txtShelf);

            Bitmap bitmap = BitmapFactory.decodeByteArray(user.image, 0, user.image.length);
            imgHinhDaidien.setImageBitmap(bitmap);
            txtTenSP.setText(user.userName);
            txtShelf.setText(user.bookShelf);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, updateUser.class);
                    intent.putExtra("userName", user.userName);
                    intent.putExtra("password", user.password);
                    intent.putExtra("image", user.image);
//                    intent.putExtra("bookShelf", user.bookShelf);
                    Log.i("in ra: ", user.userName);
                    activity.startActivity(intent);
                }
            });
        }
        return view;
    }
}
