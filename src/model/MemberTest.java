package model;

import exception.BookNotAvailableException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    private Member member;
    private Book book;

    @BeforeEach
    void setUp() {
        member = new Member(1, "Əli", "ali@mail.com");
        book = new Book(1, "Clean Code", "Robert Martin", 2008, BookCategory.TECHNOLOGY);
    }

    @Test
    void borrow_availableBook_addsToList() throws Exception {
        member.borrow(book);
        assertEquals(1, member.getBorrowCount());
        assertFalse(book.isAvailable());
    }
    @Test
    void borrow_unavailableBook_throwsException() {
        book.setAvailable(false);

        assertThrows(BookNotAvailableException.class, () -> member.borrow(book));
        assertEquals(0, member.getBorrowCount());   // siyahıya düşməməlidir
    }

    @Test
    void returnBook_afterBorrow_makesBookAvailable() throws Exception {
        member.borrow(book);

        member.returnBook(book);

        assertTrue(book.isAvailable());
        assertEquals(0, member.getBorrowCount());
}
}