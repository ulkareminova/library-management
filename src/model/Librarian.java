package model;

public class Librarian extends Person{
    String employeeNumber;




    public Librarian(int id, String name, String email, String s) {
        super(id, name, email);
        this.employeeNumber=employeeNumber;
    }
   public String getEmployeeNumber() {
        return employeeNumber;
    }
//    public void setEmployeeNumber(String employeeNumber) {}
    @Override
    public String getRole() {
        return "LIBRARIAN";
    }
}
