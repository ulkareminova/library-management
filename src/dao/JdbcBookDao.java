package dao;

import model.Book;
import model.BookCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcBookDao implements BookDao {

    private final Connection conn;

    public JdbcBookDao(Connection conn) throws SQLException {
        this.conn = conn;
        initSchema();
    }

    private void initSchema() throws SQLException {
        try (Statement st = conn.createStatement()) {
           // st.execute("DROP TABLE IF EXISTS books");
            st.execute("""
                    CREATE TABLE IF NOT EXISTS books (
                        id INT PRIMARY KEY,
                        title VARCHAR(200) NOT NULL,
                        author VARCHAR(100) NOT NULL,
                        publish_year INT,
                        category VARCHAR(30),
                        available BOOLEAN DEFAULT TRUE
                    )""");
        }
    }

    @Override
    public void save(Book book) throws SQLException {
        String sql = """
                MERGE INTO books (id, title, author, publish_year, category, available)
                KEY (id)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, book.getId());
            ps.setString(2, book.getTittle());
            ps.setString(3, book.getAuthor());
            ps.setInt(4, book.getYear());
            ps.setString(5, book.getCategory().name());
            ps.setBoolean(6, book.isAvailable());
            ps.executeUpdate();
        }
    }

    @Override
    public Optional<Book> findById(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Book> findAll() throws SQLException {
        List<Book> result = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        }
        return result;
    }

    @Override
    public void update(Book book) throws SQLException {
        String sql = """
                UPDATE books
                SET title = ?, author = ?, publish_year = ?, category = ?, available = ?
                WHERE id = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTittle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getYear());
            ps.setString(4, book.getCategory().name());
            ps.setBoolean(5, book.isAvailable());
            ps.setInt(6, book.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Book mapRow(ResultSet rs) throws SQLException {
        Book book = new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getInt("publish_year"),
                BookCategory.valueOf(rs.getString("category")));
        book.setAvailable(rs.getBoolean("available"));
        return book;
    }
}

