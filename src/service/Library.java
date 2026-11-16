package service;

import exception.DuplicateBookException;
import model.Book;
import model.BookCategory;

import java.util.*;

public class Library {
    public List<Book> getBookSortedBy(Comparator<Book> comparator) {
        List<Book> result =new ArrayList<>(books.values());
        return result;
    }
    private final Map<Integer, Book> books=new HashMap<>();
    public void addBook(Book book) {
        if (books.containsKey(book.getId())) {
            throw new DuplicateBookException("Bu id artıq mövcuddur: " + book.getId());
        }
        books.put(book.getId(), book);
    }    public Book findById(int id){
        return books.get(id);
    }
    public boolean removeBook(int id){
        return books.remove(id) != null;
    }
    public List<Book> findByAuthor(String author){
        List<Book> result=new ArrayList<>();
        for (Book book:books.values()){
            if(book.getAuthor().equalsIgnoreCase(author)){
                result.add(book);
            }
        }
        return result;

    }
    public List<Book> findByCategory(BookCategory category){
        List<Book> result=new ArrayList<>();
        for (Book book:books.values()){
            if (book.getCategory()==category){
                result.add(book);
            }
        }
        return  result;
    }
    public List<Book> getAllBooks(){
       return new ArrayList<>(books.values());
    }
   public void adDBook(Book book){
        if(books.containsKey(book.getId())){
            throw new DuplicateBookException("Bu id artiq movcuddur");
        }
   }

}
