package model;

import exception.BookNotAvailableException;

public interface Borrowable {
    void borrow(Book book)throws BookNotAvailableException;
    void returnBook(Book book);
    int getBorrowCount();
}
