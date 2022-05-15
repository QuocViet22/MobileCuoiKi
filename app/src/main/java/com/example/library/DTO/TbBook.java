package com.example.library.DTO;

public class TbBook {

    String _id;

    public TbBook(int image, String book_title, String book_author) {
        this.image = image;
        this.book_title = book_title;
        this.book_author = book_author;
    }

    int image;
    String book_title;
    String book_author;
    String book_pages;

    public TbBook(int image, String _id, String book_title, String book_author, String book_pages) {
        this.image = image;
        this._id = _id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_pages = book_pages;
    }

    public TbBook() {

    }

    public static final String TABLE_NAME = "my_library";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "book_title";
    public static final String COLUMN_AUTHOR = "book_author";
    public static final String COLUMN_PAGES = "book_pages";

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String get_id() {
        return _id;
    }

    public void setid(String book_id) {
        this._id = _id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_pages() {
        return book_pages;
    }

    public void setBook_pages(String book_pages) {
        this.book_pages = book_pages;
    }
}
