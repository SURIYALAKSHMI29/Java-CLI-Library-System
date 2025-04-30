package libraryManagement;

import java.util.*;
import com.fasterxml.jackson.annotation.*;


public class BorrowingHistory {
    @JsonProperty("isbn")
    private int isbn;

    @JsonProperty("bookName")
    private String bookName;

    @JsonProperty("userName")
    private String userName;
    
    @JsonProperty("borrowDate")
    @JsonFormat(pattern = "yyyy-MM-dd")  // Ensure the date format when converting to/from JSON
    private Date borrowDate;
    
    @JsonProperty("returnDate")
    @JsonFormat(pattern = "yyyy-MM-dd")  
    private Date returnDate;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    public BorrowingHistory() {}

    public BorrowingHistory(int isbn, String bookName, String userName, String phoneNumber, Date borrowDate, Date returnDate) {
        this.isbn = isbn;
        this.bookName = bookName;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public int getIsbn(){
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String UserName) {
        this.userName = UserName;
    }

    public Date getBorrowDate(){
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate(){
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookName() {
        return bookName;
    }
}
