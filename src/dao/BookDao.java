package dao;

import model.Book;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BookDao {
    void save(Book book) throws SQLException;
    Optional<Book> findById(int id) throws SQLException;
    List<Book> findAll() throws SQLException;
    void update(Book book) throws SQLException;
    void delete(int id) throws SQLException;

}
