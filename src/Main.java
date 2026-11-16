import dao.DataBaseConnection;
import exception.BookNotAvailableException;
import model.*;
import service.BookFileStorage;
import service.FineStrategy;
import service.Library;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        test3_library();
        test4_exceptions();
        test5_streams();
        test6_fileIO();
        test7_threads();
        test8_singleton();
        test9_strategy();
        test10_factory();
        test11_builder();
    }

    static void test3_library() {
        Library library = new Library();
        library.addBook(new Book(1, "Clean Code", "Robert Martin", 2008, BookCategory.TECHNOLOGY));
        library.addBook(new Book(2, "Martin Eden", "Jack London", 1909, BookCategory.FICTION));
        library.addBook(new Book(3, "Sapiens", "Yuval Harari", 2011, BookCategory.HISTORY));

        System.out.println(library.findByAuthor("robert martin"));
        System.out.println(library.findByCategory(BookCategory.FICTION));

        List<Book> all = library.getAllBooks();
        all.sort(Comparator.comparing(Book::getYear).reversed());
        System.out.println("Ile gore (yeni birinci): " + all);
    }

    static void test4_exceptions() {
        Member m1 = new Member(1, "Əli", "ali@mail.com");
        Member m2 = new Member(2, "Leyla", "leyla@mail.com");
        Book b1 = new Book(1, "Clean Code", "Robert Martin", 2008, BookCategory.TECHNOLOGY);
        try {
            m1.borrow(b1);
            System.out.println("m1 goturdu");
            m2.borrow(b1);
            System.out.println("m2 goturdu");
        } catch (BookNotAvailableException e) {
            System.out.println("Xeta: " + e.getMessage());
        } finally {
            System.out.println("Emeliyyat bitdi");
        }
    }

    static void test5_streams() {
        Library library = new Library();
        library.addBook(new Book(1, "Clean Code", "Robert Martin", 2008, BookCategory.TECHNOLOGY));
        library.addBook(new Book(2, "Effective Java", "Joshua Bloch", 2018, BookCategory.TECHNOLOGY));
        library.addBook(new Book(3, "Sherlock Holmes", "Arthur Conan Doyle", 1892, BookCategory.FICTION));
        library.addBook(new Book(4, "Sapiens", "Yuval Harari", 2011, BookCategory.HISTORY));
        library.addBook(new Book(5, "Clean Architecture", "Robert Martin", 2017, BookCategory.TECHNOLOGY));
        library.addBook(new Book(6, "Cosmos", "Carl Sagan", 1980, BookCategory.SCIENCE));
        library.findById(3).setAvailable(false);

        List<Book> books = library.getAllBooks();

        System.out.println("1. " + books.stream().filter(b -> b.getYear() > 2000).map(Book::getTittle).toList());
        System.out.println("2. " + books.stream().filter(Book::isAvailable).count());
        System.out.println("3. " + books.stream().max(Comparator.comparing(Book::getYear))
                .map(Book::getTittle).orElse("Kitab yoxdur"));
        System.out.println("4. " + books.stream().map(Book::getAuthor).distinct().sorted().toList());

        Map<BookCategory, Long> counts = books.stream()
                .collect(Collectors.groupingBy(Book::getCategory, Collectors.counting()));
        counts.forEach((cat, n) -> System.out.println("5. " + cat.getDisplayName() + " -> " + n));

        System.out.println("6. " + books.stream().mapToInt(Book::getYear).average().orElse(0));
        System.out.println("7. " + books.stream().map(Book::getTittle).collect(Collectors.joining(", ")));
    }

    static void test6_fileIO() throws IOException {
        Library library = new Library();
        library.addBook(new Book(1, "Clean Code", "Robert Martin", 2008, BookCategory.TECHNOLOGY));
        library.addBook(new Book(2, "Sapiens", "Yuval Harari", 2011, BookCategory.HISTORY));
        library.findById(2).setAvailable(false);

        BookFileStorage storage = new BookFileStorage();
        storage.saveBooks(library.getAllBooks(), "books.txt");

        Library library2 = new Library();
        for (Book b : storage.loadBooks("books.txt")) {
            library2.addBook(b);
        }
        System.out.println("Fayldan oxundu: " + library2.getAllBooks().size());
    }

    static void test7_threads() throws InterruptedException {
        Book book = new Book(1, "Clean Code", "Robert Martin", 2008, BookCategory.TECHNOLOGY);
        Member m1 = new Member(1, "Eli", "eli@mail.com");
        Member m2 = new Member(2, "Leyla", "leyla@mail.com");

        Runnable r1 = () -> {
            try {
                m1.borrow(book);
                System.out.println("m1 goturdu");
            } catch (BookNotAvailableException e) {
                System.out.println("m1: " + e.getMessage());
            }
        };
        Runnable r2 = () -> {
            try {
                m2.borrow(book);
                System.out.println("m2 goturdu");
            } catch (BookNotAvailableException e) {
                System.out.println("m2: " + e.getMessage());
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    static void test8_singleton() {
        DataBaseConnection a = DataBaseConnection.INSTANCE;
        DataBaseConnection b = DataBaseConnection.INSTANCE;
        System.out.println("Eyni obyekt: " + (a == b));
    }

    static void test9_strategy() {
        FineStrategy regular = days -> days * 0.5;
        FineStrategy student = days -> days * 0.2;
        System.out.println(regular.calculate(10));
        System.out.println(student.calculate(10));
    }

    static void test10_factory() {
        Person p1 = PersonFactory.create("member", 1, "Əli", "ali@mail.com");
        Person p2 = PersonFactory.create("librarian", 2, "Rəşad", "r@mail.com");
        System.out.println(p1.getRole());
        System.out.println(p2.getRole());
        try {
            PersonFactory.create("admin", 3, "X", "x@mail.com");
        } catch (IllegalArgumentException e) {
            System.out.println("Xeta: " + e.getMessage());
        }
    }

    static void test11_builder() {
        Book b = new BookBuilder()
                .id(1).title("Clean Code").author("Robert Martin")
                .year(2008).category(BookCategory.TECHNOLOGY)
                .build();
        System.out.println(b);

        try {
            new BookBuilder().id(2).build();
        } catch (IllegalArgumentException e) {
            System.out.println("Xeta: " + e.getMessage());
        }
    }
}