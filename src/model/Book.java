package model;

import java.util.Objects;

public class Book implements Comparable<Book> {
    private int id;
    private String title;
    private String author;
    private int year;
    private boolean available;
    private BookCategory category;
  //  public Book() {


    public Book(int id, String title, String author, int year, BookCategory category)
    {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.category = category;
        this.available = true;
    }

    public Book() {

    }

    public BookCategory getCategory() {
        return category;
    }
        public int getId () {
            return id;
        }

        public String getTittle () {
            return title;
        }

        public void setTittle (String tittle){
            this.title = tittle;
        }

        public String getAuthor () {
            return author;
        }

        public int getYear () {
            return year;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable ( boolean available){
            this.available = available;
        }

        @Override
        public String toString () {
            return "Book{" +
                    "id=" + id +
                    ", tittle='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", year=" + year +
                    ", available=" + available +
                    '}';
        }

        @Override
        public boolean equals (Object o){
            if (o == null || getClass() != o.getClass()) return false;
            Book book = (Book) o;
            return id == book.id;
    }

        @Override
        public int hashCode () {
            return Objects.hash(id);

}

    @Override
    public int compareTo(Book other) {
        return Integer.compare(this.year, other.year);
    }
}

