package libraryManagement;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
// The ObjectMapper is the core class in Jackson.
// It's used to convert between Java objects and JSON.

import com.fasterxml.jackson.core.type.TypeReference;
// When reading from JSON, Jackson needs to know what type of object to
// convert the JSON into — especially for generic types like List<Book>.

import java.io.File;
// To create a reference to your .json file in the filesystem.

import java.io.IOException;

public class Library {
    
    List<Book> books;
    List<BorrowingHistory> historyList;
    private String fileName;

    public Library(String fileName){
        books = new ArrayList<Book>();
        this.fileName = fileName;
        loadFromFile(fileName);
        loadBorrowingHistory();
    } 

    public void saveToFile(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), books);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }
    
    public void loadFromFile(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(filename);
            
            if (file.exists()) {
                // System.out.println("file exists, loading...");
                books = mapper.readValue(file, new TypeReference<List<Book>>() {});
                // System.out.println("Books loaded: " + books.size()); 
            } else {
                System.out.println("File not found: " + filename);  
            }
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }    

    public void saveBorrowingHistory() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("borrowingHistory_json.json"), historyList);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public void loadBorrowingHistory(){
        ObjectMapper mapper = new ObjectMapper();
        try{
            File file = new File("borrowingHistory_json.json");
            if(file.exists() && file.length() > 0){
                historyList = mapper.readValue(file, new TypeReference<List<BorrowingHistory>>() {});
                //System.out.println("history loaded: " + historyList.size()); 
            } else {
                historyList = new ArrayList<>();
            }
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }

    public void addBook(String bookName, String author, int ISBN, int count){
        for(Book book: books){
            if(book.getISBN() == ISBN){
                System.out.println("Book "+book.getBookName()+" by "+book.getAuthorName()+" with ISBN "+book.getISBN()+" already exists.");
                return;
            }
        }
        Book book = new Book(bookName, author, ISBN, count);
        books.add(book);
        saveToFile(this.fileName);
        System.out.println("Book "+bookName+" added successfully.\nAvailable Count: "+book.getAvailableCount());
    }

    public void displayBookInfo(){
        if(books.size()==0){
            System.out.println("\nSorry, No Books are available right now!");
        }
        String header = String.format("\n%-30s | %-30s | %-10s | %-10s", "Book Name", "Author Name", "ISBN", "Count");
        System.out.println(header);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < header.length(); i++) {
            sb.append("-");
        }
        System.out.println(sb.toString());
    
        for(Book book: books){
            book.displayBookInfo();
        }
        System.out.println(sb.toString());

    }

    public void deleteBook(int ISBN){
        for(Book book: books){
            if(book.getISBN() == ISBN){
                books.remove(book);
                // Removing an item from a list while iterating it can cause a ConcurrentModificationException.
                // But since you return immediately, it's safe here

                saveToFile(this.fileName);
                System.out.println("Book deleted successfully.");
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void updateBook(int ISBN, String bookName, String author, int count){
        for(Book book: books){
            if(book.getISBN() == ISBN){
                book.setBookName(bookName);
                book.setAuthorName(author);
                book.setAvailableCount(count);
                System.out.println("Book updated successfully.");
                saveToFile(this.fileName);
                book.getBookDetail();
                return;
            }
        }
        System.out.println("Book not found. Update failed.");
    }

    public void getBookDetail(int ISBN){
        for(Book book: books){
            if(book.getISBN()==ISBN){
                System.out.println("Book Found:");
                book.getBookDetail();
                return;
            }
        }
        System.out.println("Sorry,Book not found.");
    }

    public boolean bookExists(int ISBN){
        for(Book book: books){
            if(book.getISBN() == ISBN){
                return true;
            }
        }
        return false;
    }

    // Search -> compile time polymorphism
    public void search(String bookName){
        for(Book book: books){
            if(book.getBookName().equalsIgnoreCase(bookName)){
                System.out.println("Book Found:");
                book.getBookDetail();
                return;
            }
        }
        System.out.println("Sorry,Book not found. You can view our other books.");
    }

    public void search(int ISBN){
        for(Book book: books){
            if(book.getISBN() == ISBN){
                System.out.println("Book Found:");
                book.getBookDetail();
                return;
            }
        }
        System.out.println("Sorry,Book not found. You can view our other books.");
    }

    public void borrowBook(int ISBN, String UserName, String PhoneNumber){
        for(Book book: books){
            if(book.getISBN() == ISBN && book.isAvailable()){
                book.changeAvailableCount(-1);
                saveToFile(this.fileName);
                System.out.println("Book "+book.getBookName()+" by "+book.getAuthorName()+" borrowed successfully.");
               
                historyList.add(new BorrowingHistory(book.getISBN(), book.getBookName(), UserName, PhoneNumber, new Date(), null)); 
                saveBorrowingHistory();
                return;
            }
        }
        System.out.println("Sorry,Book not found. You can view our other books.");
    }

    public void returnBook(int ISBN,  String UserName, String PhoneNumber){
        boolean found = false;
        for(BorrowingHistory history: historyList){
            if(history.getIsbn() == ISBN && history.getUserName().equalsIgnoreCase(UserName) 
                && history.getPhoneNumber().equals(PhoneNumber)
                && history.getReturnDate()==null){
                    
                    history.setReturnDate(new Date());
                    saveBorrowingHistory();
                    found = true;
                    break;
            }
        }
        if(found){
            for(Book book: books){
                if(book.getISBN() == ISBN){
                    book.changeAvailableCount(1);
                    saveToFile(this.fileName);
                    System.out.println("Book "+book.getBookName()+" by "+book.getAuthorName()+" returned successfully.");
                    return;
                }
            }
        }
        else{
            System.out.println("Book not found.");
        }
    }

    public void displayBorrowingHistory(int isbn) {
        int count=1;
        for (BorrowingHistory history : historyList) {
            if (history.getIsbn() == isbn) {
                System.out.println(count+
                        "\tBook: " + history.getBookName() +
                        "\tUser Name: " + history.getUserName() +  
                        "\tPhone Number:" + history.getPhoneNumber() +
                        "\tBorrowed on: " + history.getBorrowDate() +
                        "\tReturned on: " + (history.getReturnDate()==null? "Not Returned Yet":history.getReturnDate()));
                count++;
            }
        }
        if(count==1)
            System.out.println("\nSorry, No Books are borrowed right now!");
    }

    public void displayUnreturnedBooks() {
        if(historyList.size()==0){
            System.out.println("\nSorry, No Books are borrowed right now!");
        }
        for (BorrowingHistory history : historyList) {
            if (history.getReturnDate()==null) {
                System.out.println("ISBN: " + history.getIsbn() +
                        "\tBook: " + history.getBookName() +
                        "\tUser Name: " + history.getUserName() +  
                        "\tPhone Number:" + history.getPhoneNumber() +
                        "\tBorrowed on: " + history.getBorrowDate()
                        );
            }
        }
    }
    
    public void viewBorrowedBooks(String userName, String phoneNumber) {
        for (BorrowingHistory history : historyList) {
            if (history.getUserName().equalsIgnoreCase(userName) && history.getPhoneNumber().equals(phoneNumber)) {
                System.out.println("ISBN: " + history.getIsbn() +
                        "\tBook: " + history.getBookName() +
                        "\tBorrowed on: " + history.getBorrowDate()+
                        "\tReturned on: " + (history.getReturnDate()==null? "Not Returned Yet":history.getReturnDate()));
            }
        }
    }

}