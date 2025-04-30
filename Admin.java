package libraryManagement;
import java.util.*;

public class Admin {
    Library lib;
    Scanner obj;

    public Admin(Library library) {
        this.lib = library;
        obj = new Scanner(System.in);
        verifyAdmin();
    }

    public void verifyAdmin(){
        System.out.print("Enter Password: ");
        int chances = 2;
        while(true) {
            try {
                String password = obj.next();
                if(password.equals("laksh!")) {
                    adminFunctionality();
                    return;
                } else if(chances > 0) {
                    System.out.println("Invalid Password. Try Again, you have "+chances+" chances left.\n");
                    chances--;
                }
                else{
                    System.out.println("You have used all your chances. Try again later.\n");
                    System.exit(0);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Try Again.");
                e.printStackTrace();
            }
        }
    }

    void adminFunctionality() {
        System.out.println("\n\n-------------- ADMIN PANEL --------------");

        while(true){
            System.out.println("\n1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. Update Book");
            System.out.println("4. View Borrowing History");
            System.out.println("5. View Unreturned Books");
            System.out.println("6. View All Books");
            System.out.println("7. Back");

            int option = obj.nextInt();

            switch (option) {
                case 1:
                    obj.nextLine(); 
                    System.out.print("Enter Book Name: ");
                    String bookName = obj.nextLine();
                    System.out.print("Enter Author Name: ");
                    String authorName = obj.nextLine();
                    System.out.print("Enter Book ISBN: ");
                    int ISBN = getValidNumber();
                    System.out.print("Enter Available Count: ");
                    int count = getValidNumber();
                    lib.addBook(bookName, authorName, ISBN, count);
                    break;
                
                case 2:
                    System.out.print("Enter Book ISBN: ");
                    int isbnNo = getValidNumber();
                    lib.deleteBook(isbnNo);
                    break;

                case 3:
                    System.out.print("Enter Book ISBN: ");
                    int isbnNo1 = getValidNumber();
                    if(lib.bookExists(isbnNo1) == false) {
                        System.out.println("Book not found.");
                        break;
                    }
                    lib.getBookDetail(isbnNo1);
                    System.out.print("\nEnter the details below... \nBook Name: ");
                    String bookName1 = obj.nextLine();
                    System.out.print("Author Name: ");
                    String authorName1 = obj.nextLine();
                    System.out.print("Available Count: ");
                    int count1 = getValidNumber();
                    lib.updateBook(isbnNo1, bookName1, authorName1, count1);
                    break;
                
                case 4:
                    System.out.print("Enter Book ISBN to view borrowing history: ");
                    int bookIsbn = getValidNumber();
                    lib.displayBorrowingHistory(bookIsbn);
                    break;
                
                case 5:
                    lib.displayUnreturnedBooks(); 
                     break;

                case 6:
                    lib.displayBookInfo();
                    break;
                
                case 7:
                    return;
                default:
                    System.out.println("Invalid Option. Please try again.");
            }
        }
    }

    public int getValidNumber() {
        int num = 0;
        while(true) {
            try {
                num = obj.nextInt();
                obj.nextLine();
                if(num < 0) {
                    System.out.println("Value must be a positive number.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                obj.nextLine();
            }
        }
        return num;
    }
    
}
