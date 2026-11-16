package model;

public class PersonFactory {
    public static Person create(String type, int id, String name, String email) {
        if ("member".equalsIgnoreCase(type)) {
            return new Member(id, name, email);
        } else if ("librarian".equalsIgnoreCase(type)) {
            return new Librarian(id, name, email, "EMP-" + id);
        } else {
            throw new IllegalArgumentException("Naməlum tip: " + type);
        }
    }
}