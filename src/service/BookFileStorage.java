package service;

import model.Book;
import model.BookCategory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BookFileStorage {
    public void saveBooks(List<Book> books, String fileName) throws IOException {
        List<String> lines = books.stream().map(B -> B.getYear() + "," + B.getTittle() + "," + B.getAuthor() + "," + B.getYear() + "," + B.getCategory() + "," + B.isAvailable()).toList();

        Files.write(Path.of(fileName), lines);

    }

    public List<Book> loadBooks(String fileName) throws IOException {
        List<Book> result = new ArrayList<>();
        Path path = Path.of(fileName);
        if (Files.exists(path)) {
            return result;
        }
        for (String line : Files.readAllLines(path)) {
            try {

                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                String author = parts[2];
                int year = Integer.parseInt(parts[3]);
                BookCategory category = BookCategory.valueOf(parts[4]);
                boolean isAvailable = Boolean.parseBoolean(parts[5]);

                Book book = new Book(id, title, author, year, category);
                book.setAvailable(isAvailable);
                result.add(book);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("sehv setir: " +line );
            }
        }
        return result;
    }


}



