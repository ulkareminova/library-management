package model;

import exception.BookNotAvailableException;

import java.util.ArrayList;
import java.util.List;

public class Member extends Person implements Borrowable {
    private List<Book> borrowedBooks = new ArrayList<>();


    public Member(int id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public String getRole() {
        return "Member";
    }

    @Override
    public void borrow(Book book) throws BookNotAvailableException {
        if(!book.isAvailable()) {
         throw new BookNotAvailableException("Kitab movcud deyil");
        }
        book.setAvailable(false);
        borrowedBooks.add(book);
    }

    @Override
    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.setAvailable(true);
        }
    }

    @Override
    public int getBorrowCount() {
        return borrowedBooks.size();
    }


    @Override
    public String toString() {
        return "Member[id=" + getId() + ", name=" + getName() + ", email=" + getEmail() + "]";
    }
}
