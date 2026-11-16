import dao.BookDao;
import dao.JdbcBookDao;
import model.Book;
import model.BookCategory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoDemo {
    public static void main(String[] args){
             try(Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "")){
                 BookDao dao = new JdbcBookDao(conn);
                 dao.save(new Book(1, "Clean Code", "Robert Martin", 2008, BookCategory.TECHNOLOGY));
                 dao.save(new Book(2, "Sapiens", "Yuval Harari", 2011, BookCategory.HISTORY));
                 dao.save(new Book(3, "Cosmos", "Carl Sagan", 1980, BookCategory.SCIENCE));

                 System.out.println("Butun kitablar: "+ dao.findAll());

                 Book b = dao.findById(1).orElseThrow();
                 b.setAvailable(false);
                 dao.update(b);
                 System.out.println("Yeniləndi: " + dao.findById(1).orElseThrow());   // ← bu sətir olmalıdır
                 dao.delete(3);
                 System.out.println("Silindikden sonra say: "+ dao.findAll());
                 System.out.println("id=99: " + dao.findById(99));
                // Book b=dao.findById(1).orElseThrow();
                 //b.setAvailable(false);
                 //dao.update(b);
                 //System.out.println("Silindikden sonra say: "+ dao.findAll().size());
                 //System.out.println("id=99: " + dao.findById(99));

             } catch (SQLException e) {
                 e.printStackTrace();

             }

    }

}
