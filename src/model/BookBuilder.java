package model;

import com.sun.org.apache.xalan.internal.lib.ExsltDatetime;

public class BookBuilder {
    private int id;
    private String title;
    public String author;
    private int year;
    private BookCategory category;

    public BookBuilder id(int id) {
        this.id = id;
        return this;
    }
    public BookBuilder title(String title) {
        this.title = title;
        return this;
    }
    public BookBuilder author(String author) {
        this.author = author;
        return this;
    }

    public BookBuilder year(int year) {
        this.year = year;
        return this;
    }

    public BookBuilder category(BookCategory category) {
        this.category = category;
        return this;
    }
    public  Book build() {
        if( title == null || author == null ){
            throw new IllegalArgumentException("Title or Author is null ve yazmag mecburidir");

        }
        return new Book(id, title, author, year, category);
    }


    }

