package libraryManagement;
import com.fasterxml.jackson.annotation.*;

public class Book {
    private String bookName;
    private String authorName;

    @JsonProperty("isbn")
    private int ISBN;
    private int availableCount;

     // Default constructor for Jackson
     public Book() {}

    public Book(String bookName, String authorName, int ISBN, int availableCount) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.ISBN = ISBN;
        this.availableCount = availableCount;
    }

    public void displayBookInfo() {
        System.out.printf("%-30s | %-30s | %-10d | %-10d\n", bookName, authorName, ISBN, availableCount);
    }

    public void getBookDetail() {
        System.out.println("Book Name: "+bookName+"\nAuthor Name: "+authorName+"\nISBN: "+ISBN+"\nAvailable Count: "+availableCount);
    }

    public void setAvailableCount(int count){
        this.availableCount = count;
    }

    public void changeAvailableCount(int count){
        this.availableCount += count;
    }

    public void setAuthorName(String authorName){
        this.authorName = authorName;
    }

    public void setBookName(String bookName){
        this.bookName = bookName;
    }

    public String getBookName() {
        return bookName;
    }

    public int getISBN(){
        return ISBN;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    @JsonIgnore
    public boolean isAvailable() {
        return availableCount > 0;
    }
}
