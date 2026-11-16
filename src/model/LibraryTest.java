package model;

import exception.DuplicateBookException;
import model.Book;
import model.BookCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Library;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
    }

    @Test
    void addBook_thenFindById_returnsSameBook() {
        Book book = new Book(1, "Clean Code", "Robert Martin", 2008, BookCategory.TECHNOLOGY);

        library.addBook(book);

        assertEquals(book, library.findById(1));
        assertNull(library.findById(99));          // olmayan id → null
    }

    @Test
    void addBook_duplicateId_throwsException() {
        library.addBook(new Book(1, "A", "X", 2000, BookCategory.FICTION));
        Book duplicate = new Book(1, "B", "Y", 2010, BookCategory.SCIENCE);

        assertThrows(DuplicateBookException.class, () -> library.addBook(duplicate));
        assertEquals(1, library.getAllBooks().size());
    }

    @Test
    void findByAuthor_ignoresCase() {
        library.addBook(new Book(1, "Clean Code", "Robert Martin", 2008, BookCategory.TECHNOLOGY));
        library.addBook(new Book(2, "Sapiens", "Yuval Harari", 2011, BookCategory.HISTORY));

        List<Book> result = library.findByAuthor("robert martin");

        assertEquals(1, result.size());
        assertEquals("Clean Code", result.get(0).getTittle());
    }
}