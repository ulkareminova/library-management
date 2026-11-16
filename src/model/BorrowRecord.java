package model;

import java.time.LocalDate;

public record BorrowRecord(Member member, Book book, LocalDate BorrowDate) {
    public BorrowRecord{
    if(member==null || book==null){
        throw new IllegalArgumentException("member ve book null ola bilmez");
    }

}
}
