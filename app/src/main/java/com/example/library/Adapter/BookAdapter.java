package com.example.library.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.library.DTO.TbBook;
import com.example.library.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> implements Filterable {

    private List<TbBook> mListBooks;
    private List<TbBook> mListBooksOld;


    public BookAdapter(List<TbBook> mListBooks) {
        this.mListBooks = mListBooks;
        this.mListBooksOld = mListBooks;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        TbBook tbBook = mListBooks.get(position);
        if (tbBook == null) {
            return;
        }
        holder.imgBook.setImageResource(tbBook.getImage());
        holder.book_title.setText(tbBook.getBook_title());
        holder.book_author.setText(tbBook.getBook_author());
    }

    @Override
    public int getItemCount() {
        if (mListBooks != null) {
            return mListBooks.size();
        }
        return 0;
    }


    public class BookViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgBook;
        private TextView book_title, book_author;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.img_user);
            book_title = itemView.findViewById((R.id.book_title_txt));
            book_author = itemView.findViewById(R.id.book_author_txt);

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    mListBooks = mListBooksOld;
                } else {
                    List<TbBook> list = new ArrayList<>();
                    for (TbBook tbBook : mListBooksOld) {
                        if (tbBook.getBook_title().contains(strSearch.toLowerCase())) {
                            list.add(tbBook);
                        }
                    }

                    mListBooks = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListBooks;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                mListBooks = (List<TbBook>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
