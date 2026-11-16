import java.sql.*;

public class JdbcDemo {

    public static void main(String[] args) {
        // String url = "jdbc:h2:./librarydb";
        String url ="jdbc:h2:mem:testdb";


        try (Connection conn = DriverManager.getConnection(url, "sa", "")) {

            try (Statement st = conn.createStatement()) {
                st.execute("""
                        CREATE TABLE IF NOT EXISTS books (
                            id INT PRIMARY KEY,
                            title VARCHAR(200) NOT NULL,
                            author VARCHAR(100) NOT NULL,
                            publication_year INT,
                            category VARCHAR(30),
                            available BOOLEAN DEFAULT TRUE
                        )""");
            }


            String insert = """
                    MERGE INTO books
                    (id, title, author, publication_year, category)
                    KEY (id) VALUES (?, ?, ?, ?, ?)
                    """;

            try (PreparedStatement ps = conn.prepareStatement(insert)) {
                ps.setInt(1, 1);
                ps.setString(2, "Clean Code");
                ps.setString(3, "Robert Martin");
                ps.setInt(4, 2008);
                ps.setString(5, "TECHNOLOGY");
                ps.executeUpdate();
            }

            // 3. Kitabları oxu
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM books WHERE publication_year > ?")) {

                ps.setInt(1, 2000);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        System.out.println(
                                rs.getInt("id") + " " +
                                        rs.getString("title")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
